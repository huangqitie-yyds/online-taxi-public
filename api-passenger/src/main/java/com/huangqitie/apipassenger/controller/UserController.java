package com.huangqitie.apipassenger.controller;

import com.huangqitie.apipassenger.service.UserService;
import com.huangqitie.internalcommon.dto.ResponseResult;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseResult getUsers(HttpServletRequest request) {
        //从HttpServletRequest获取accessToken
        String accessToken = request.getHeader("Authorization");
        return userService.getUserByAccessToken(accessToken);
    }
}
