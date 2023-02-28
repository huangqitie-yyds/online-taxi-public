package com.huangqitie.controller;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.request.VerificationCodeDTO;
import com.huangqitie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("手机号是:" + passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }
}
