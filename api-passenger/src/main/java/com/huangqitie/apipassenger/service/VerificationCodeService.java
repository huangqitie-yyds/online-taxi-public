package com.huangqitie.apipassenger.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.huangqitie.apipassenger.remote.ServicePassengerUserClient;
import com.huangqitie.apipassenger.remote.ServiceVerificationCodeClient;
import com.huangqitie.internalcommon.constant.CommonStatus;
import com.huangqitie.internalcommon.constant.IdentityStatus;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.request.VerificationCodeDTO;
import com.huangqitie.internalcommon.response.NumberCodeResponse;
import com.huangqitie.internalcommon.response.TokenResponse;
import com.huangqitie.internalcommon.util.JwtUtils;
import com.huangqitie.internalcommon.util.RedisPrefixUtils;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;


    /**
     * 生成验证码
     *
     * @param passengerPhone
     * @return
     */
    public ResponseResult generateCode(String passengerPhone) {
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        //key，value，过期时间
        String key = RedisPrefixUtils.generateKeyByPhone(passengerPhone);
        //存入Redis
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);
        //通过短信服务商，将对应的验证码发送到手机上
        return ResponseResult.success("");
    }

    /**
     * 检验验证码
     *
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        //根据手机号去redis中读取验证码
        System.out.println("根据手机号去redis中读取验证码");
        //生成key
        String key = RedisPrefixUtils.generateKeyByPhone(passengerPhone);
        //根据key从redis中获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的value:" + codeRedis);
        //校验验证码
        if (StringUtils.isBlank(codeRedis) || !verificationCode.trim().equals(codeRedis.trim())) {
            return ResponseResult.fail(CommonStatus.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatus.VERIFICATION_CODE_ERROR.getValue());
        }
        //判断原来是否有用户，并进行对应的处理
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);
        //颁发令牌
        String token = JwtUtils.generateToken(passengerPhone, IdentityStatus.PASSENGER_IDENTITY);
        String tokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, IdentityStatus.PASSENGER_IDENTITY);
        //令牌存储到redis
        stringRedisTemplate.opsForValue().set(tokenKey, token, 30, TimeUnit.DAYS);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        return ResponseResult.success(tokenResponse);
    }
}
