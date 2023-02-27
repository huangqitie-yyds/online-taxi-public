package com.huangqitie.internalcommon.constant.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "hqt@@!!com";

    //生成Token
    private static String generateToken(Map<String, String> map) {
        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(date).sign(Algorithm.HMAC256(SIGN));
        return token;
    }
    //解析Token

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("username","zhang san");
        map.put("age","18");
        String s = generateToken(map);
        System.out.println(s);
    }
}
