package com.huangqitie.serviceverificationcode.controller;

import com.huangqitie.internalcommon.constant.dto.ResponseResult;
import com.huangqitie.internalcommon.constant.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        System.out.println("size:" + size);
        double mathRandom = (Math.random() * 9 + 1) * (Math.pow(10, size - 1));
        int intCode = (int) mathRandom;
        System.out.println("generator code:" + intCode);
        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(intCode);
        return ResponseResult.success(numberCodeResponse);
    }
}
