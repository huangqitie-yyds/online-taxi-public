package com.huangqitie.apipassenger.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.huangqitie.internalcommon.constant.CommonStatus;
import com.huangqitie.internalcommon.constant.TokenConstants;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.dto.TokenResult;
import com.huangqitie.internalcommon.response.TokenResponse;
import com.huangqitie.internalcommon.util.JwtUtils;
import com.huangqitie.internalcommon.util.RedisPrefixUtils;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshToken) {
        //解析refreshToken
        TokenResult tokenResult = JwtUtils.checkToken(refreshToken);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatus.TOKEN_ERROR.getCode(), CommonStatus.TOKEN_ERROR.getValue());
        }
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        //解析出的参数拼接成key去Redis取值进行校验
        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);
        if (StringUtils.isBlank(refreshTokenRedis) || !refreshToken.trim().equals(refreshTokenRedis.trim())) {
            return ResponseResult.fail(CommonStatus.TOKEN_ERROR.getCode(), CommonStatus.TOKEN_ERROR.getValue());
        }

        //生成双Token
        String newRefreshToken = JwtUtils.generateToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String newAccessToken = JwtUtils.generateToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

        String accessTokenKey = RedisPrefixUtils.generateTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

        stringRedisTemplate.opsForValue().set(accessTokenKey, newAccessToken, 30, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, newRefreshToken, 1, TimeUnit.MINUTES);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(newRefreshToken);
        tokenResponse.setAccessToken(newAccessToken);
        return ResponseResult.success(tokenResponse);
    }
}
