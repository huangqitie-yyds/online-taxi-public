package com.huangqitie.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.huangqitie.internalcommon.dto.TokenResult;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "hqt@@!!com";

    private static final String JWT_PHONE_KEY = "phone";

    private static final String JWT_IDENTITY_KEY = "identity";

    //生成Token
    public static String generateToken(String passengerPhone, String identity) {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_PHONE_KEY, passengerPhone);
        map.put(JWT_IDENTITY_KEY, identity);
        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        JWTCreator.Builder builder = JWT.create();
        map.forEach(
                (k, v) -> {
                    builder.withClaim(k, v);
                }
        );
        //设置Token的过期时间
//        builder.withExpiresAt(date);
        String token = builder.sign(Algorithm.HMAC256(SIGN));
        return token;
    }

    //解析Token
    public static TokenResult parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_PHONE_KEY).asString();
        String identity = verify.getClaim(JWT_IDENTITY_KEY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    public static void main(String[] args) {
        String s = generateToken("18273932402", "1");
        System.out.println(s);
        System.out.println(parseToken(s));
    }
}
