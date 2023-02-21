package com.huangqitie.apipassenger.controller;

import com.huangqitie.apipassenger.request.VerificationCodeDTO;
import com.huangqitie.apipassenger.service.VerificationCodeService;
import com.huangqitie.internalcommon.constant.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("接收到的手机号码是:" + passengerPhone);
        return verificationCodeService.generateCode(passengerPhone);
    }
}
