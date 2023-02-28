package com.huangqitie.apipassenger.controller;

import com.huangqitie.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "test api passenger";
    }

    @GetMapping("/authTest")
    public ResponseResult authTest() {
        return ResponseResult.success("auth");
    }

    @GetMapping("/noAuthTest")
    public ResponseResult noAuthTest() {
        return ResponseResult.success("no auth");
    }
}
