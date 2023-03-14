package com.huangqitie.service;

import com.huangqitie.internalcommon.constant.CommonStatus;
import com.huangqitie.internalcommon.dto.PassengerUser;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.mapper.PassengerUserMapper;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("user service");
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        //判断用户是否存在
        if (passengerUsers.size() == 0) {
            //用户信息不存在
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("李四");
            passengerUser.setPassengerGender((byte) 1);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setState((byte) 0);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setCreateTime(now);
            passengerUser.setUpdateTime(now);
            passengerUserMapper.insert(passengerUser);
        }
        return ResponseResult.success();
    }

    /**
     * 根据手机号查询用户信息
     * @param passengerPhone
     * @return
     */
    public ResponseResult getUserByPhone(String passengerPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if (passengerUsers.size() == 0) {
            return ResponseResult.fail(CommonStatus.USER_NOT_EXISTS.getCode(),
                    CommonStatus.USER_NOT_EXISTS.getValue());
        } else {
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }
}
