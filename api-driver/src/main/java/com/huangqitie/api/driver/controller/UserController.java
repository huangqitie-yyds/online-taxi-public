package com.huangqitie.api.driver.controller;

import com.huangqitie.api.driver.service.UserService;
import com.huangqitie.internalcommon.dto.DriverUser;
import com.huangqitie.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return userService.updateUser(driverUser);
    }
}
