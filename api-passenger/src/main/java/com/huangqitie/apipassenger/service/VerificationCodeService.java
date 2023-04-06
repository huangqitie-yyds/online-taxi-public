package com.huangqitie.apipassenger.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.huangqitie.apipassenger.remote.ServicePassengerUserClient;
import com.huangqitie.apipassenger.remote.ServiceVerificationCodeClient;
import com.huangqitie.internalcommon.constant.CommonStatus;
import com.huangqitie.internalcommon.constant.IdentityStatus;
import com.huangqitie.internalcommon.constant.TokenConstants;
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
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone,IdentityStatus.PASSENGER_IDENTITY);
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
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone,IdentityStatus.PASSENGER_IDENTITY);
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
        String accessToken = JwtUtils.generateToken(passengerPhone, IdentityStatus.PASSENGER_IDENTITY,
                TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(passengerPhone, IdentityStatus.PASSENGER_IDENTITY,
                TokenConstants.REFRESH_TOKEN_TYPE);
        //令牌存储到redis
        String accessTokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, IdentityStatus.PASSENGER_IDENTITY,
                TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.SECONDS);

        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, IdentityStatus.PASSENGER_IDENTITY,
                TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 1, TimeUnit.MINUTES);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
