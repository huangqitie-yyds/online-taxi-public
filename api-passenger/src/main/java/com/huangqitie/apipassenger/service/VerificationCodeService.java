package com.huangqitie.apipassenger.service;

import com.alibaba.fastjson.JSONObject;
import com.huangqitie.apipassenger.remote.ServiceVerificationCodeClient;
import com.huangqitie.internalcommon.constant.dto.ResponseResult;
import com.huangqitie.internalcommon.constant.response.NumberCodeResponse;
import com.huangqitie.internalcommon.constant.response.TokenResponse;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        String key = verificationCodePrefix + passengerPhone;
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
        //校验验证码
        System.out.println("校验验证码");
        //判断原来是否有用户，并进行对应的处理
        System.out.println("判断原来是否有用户，并进行对应的处理");
        //颁发令牌
        System.out.println("颁发令牌");
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token string");
        return ResponseResult.success(tokenResponse);
    }
}