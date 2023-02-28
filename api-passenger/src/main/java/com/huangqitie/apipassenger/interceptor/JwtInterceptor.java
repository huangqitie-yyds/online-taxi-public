package com.huangqitie.apipassenger.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.StringUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.dto.TokenResult;
import com.huangqitie.internalcommon.util.JwtUtils;
import com.huangqitie.internalcommon.util.RedisPrefixUtils;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean result = true;
        String resultString = "";
        String token = request.getHeader("Authorization");
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (SignatureVerificationException e) {
            result = false;
            resultString = "token sign error";
        } catch (TokenExpiredException e) {
            result = false;
            resultString = "token time out";
        } catch (AlgorithmMismatchException e) {
            result = false;
            resultString = "token algorithm is match exception";
        } catch (Exception e) {
            result = false;
            resultString = "token invalid";
        }
        if (tokenResult == null) {
            result = false;
            resultString = "token invalid";
        } else {
            //拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generateTokenKey(phone, identity);
            //从redis中取出token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            //验证传入的Token和Redis中存储的是否一致
            if (StringUtils.isBlank(tokenRedis)) {
                result = false;
                resultString = "token invalid";
            } else {
                if (!token.trim().equals(tokenRedis.trim())) {
                    result = false;
                    resultString = "token invalid";
                }
            }
        }

        if (!result) {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(JSONObject.toJSONString(ResponseResult.fail(resultString)));
        }
        return result;
    }

}
