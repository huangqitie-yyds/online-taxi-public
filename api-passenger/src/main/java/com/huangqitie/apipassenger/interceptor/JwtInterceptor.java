package com.huangqitie.apipassenger.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.util.JwtUtils;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean result = true;
        String resultString = "";
        String token = request.getHeader("Authorization");
        try {
            JwtUtils.parseToken(token);
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
        if (!result) {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(JSONObject.toJSONString(ResponseResult.fail(resultString)));
        }
        return result;
    }

}
