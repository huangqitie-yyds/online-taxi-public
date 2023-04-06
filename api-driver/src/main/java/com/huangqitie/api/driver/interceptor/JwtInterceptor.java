package com.huangqitie.api.driver.interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.huangqitie.internalcommon.constant.TokenConstants;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.dto.TokenResult;
import com.huangqitie.internalcommon.util.JwtUtils;
import com.huangqitie.internalcommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");
        // 解析token
        TokenResult tokenResult = JwtUtils.checkToken(token);

        if (tokenResult == null) {
            resultString = "access token invalid";
            result = false;
        } else {
            // 拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();

            String tokenKey = RedisPrefixUtils.generateTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
            // 从redis中取出token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if ((StringUtils.isBlank(tokenRedis)) || (!token.trim().equals(tokenRedis.trim()))) {
                resultString = "access token invalid";
                result = false;
            }
        }
        if (!result) {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(JSONObject.toJSONString(ResponseResult.fail(resultString)));
        }

        return result;
    }
}