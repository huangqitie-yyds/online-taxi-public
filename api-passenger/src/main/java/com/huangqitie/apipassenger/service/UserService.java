package com.huangqitie.apipassenger.service;

import com.huangqitie.apipassenger.remote.ServicePassengerUserClient;
import com.huangqitie.internalcommon.dto.PassengerUser;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.dto.TokenResult;
import com.huangqitie.internalcommon.request.VerificationCodeDTO;
import com.huangqitie.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken) {
        //解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        ResponseResult<PassengerUser> userByPhone = servicePassengerUserClient.getUserByPhone(phone);
        return ResponseResult.success(userByPhone);
    }

}
