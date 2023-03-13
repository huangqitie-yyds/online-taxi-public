package com.huangqitie.apipassenger.service;

import com.huangqitie.internalcommon.dto.PassengerUser;
import com.huangqitie.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    public ResponseResult getUserByAccessToken(String accessToken) {
        //解析accessToken，拿到手机号
        //根据手机号查询用户信息
        log.info("accessToken:" + accessToken);
        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("张三");
        passengerUser.setProfilePhoto("头像");
        return ResponseResult.success(passengerUser);
    }

}
