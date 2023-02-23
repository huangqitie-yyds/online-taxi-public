package com.huangqitie.service;

import com.huangqitie.internalcommon.constant.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("user service");
        //根据手机号查询用户信息
        //判断用户是否存在
        //如果用户不存在，插入用户信息
        return ResponseResult.success();
    }
}
