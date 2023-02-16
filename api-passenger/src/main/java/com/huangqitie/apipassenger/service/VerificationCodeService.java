package com.huangqitie.apipassenger.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {
    public String generateCode(String passengerPhone) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("message", "success");
        System.out.println("存入redis");
        return jsonObject.toString();
    }
}
