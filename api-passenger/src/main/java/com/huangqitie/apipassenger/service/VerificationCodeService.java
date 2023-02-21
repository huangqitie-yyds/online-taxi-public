package com.huangqitie.apipassenger.service;

import com.alibaba.fastjson.JSONObject;
import com.huangqitie.apipassenger.remote.ServiceVerificationCodeClient;
import com.huangqitie.internalcommon.constant.dto.ResponseResult;
import com.huangqitie.internalcommon.constant.response.NumberCodeResponse;
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
}
