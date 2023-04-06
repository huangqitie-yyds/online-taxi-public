package com.huangqitie.api.driver.controller;

import com.huangqitie.api.driver.service.VerificationCodeService;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.request.VerificationCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String driverPhone = verificationCodeDTO.getDriverPhone();
        log.info("司机的号码：" + driverPhone);
        return verificationCodeService.checkAndSendVerificationCode(driverPhone);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){

        String driverPhone = verificationCodeDTO.getDriverPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();

        System.out.println("手机号"+driverPhone+",验证码："+verificationCode);

        return verificationCodeService.checkCode(driverPhone,verificationCode);
    }
}

