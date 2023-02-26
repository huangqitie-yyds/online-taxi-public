package com.huangqitie.service;

import com.huangqitie.internalcommon.constant.dto.ResponseResult;
import com.huangqitie.mapper.PassengerUserMapper;
import com.huangqitie.pojo.PassengerUser;
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
        System.out.println(passengerUsers.size()==0?"无记录":passengerUsers.get(0).getPassengerPhone());
        //判断用户是否存在
        //如果用户不存在，插入用户信息
        return ResponseResult.success();
    }
}
