package com.huangqitie.api.driver.service;

import com.huangqitie.api.driver.remote.ServiceDriverUserClient;
import com.huangqitie.api.driver.remote.ServiceVerificationCodeClient;
import com.huangqitie.internalcommon.constant.CommonStatus;
import com.huangqitie.internalcommon.constant.DriverCarConstants;
import com.huangqitie.internalcommon.constant.IdentityStatus;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.DriverUserExistsResponse;
import com.huangqitie.internalcommon.response.NumberCodeResponse;
import com.huangqitie.internalcommon.util.RedisPrefixUtils;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;


    @Autowired
    ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        // 查询 service-driver-user，该手机号的司机是否存在
        ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult =
                serviceDriverUserClient.checkDriver(driverPhone);
        DriverUserExistsResponse data = driverUserExistsResponseResponseResult.getData();
        int ifExists = data.getIfExists();
        if (ifExists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatus.DRIVER_NOT_EXITST.getCode(),
                    CommonStatus.DRIVER_NOT_EXITST.getValue());
        }
        log.info(driverPhone + " 的司机存在");
        // 获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResult = serviceVerificationCodeClient.getNumberCode(6);
        NumberCodeResponse numberCodeResponse = numberCodeResult.getData();
        int numberCode = numberCodeResponse.getNumberCode();
        log.info("验证码" + numberCode);
        // 调用第三方发生验证码,第三方：阿里短信服务，腾讯，华信，容联

        // 存入redis 1：key，2：存入value
        String key = RedisPrefixUtils.generatorKeyByPhone(driverPhone, IdentityStatus.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);
        return ResponseResult.success("");
    }
}
