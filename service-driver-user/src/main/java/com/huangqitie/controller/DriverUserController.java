package com.huangqitie.controller;


import com.alibaba.fastjson.JSONObject;
import com.huangqitie.internalcommon.constant.DriverCarConstants;
import com.huangqitie.internalcommon.dto.DriverUser;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.DriverUserExistsResponse;
import com.huangqitie.service.DriverUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hqt
 * @since 2023-03-27
 */
@RestController
@Slf4j
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
     * 新增司机
     *
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {
        log.info(JSONObject.toJSONString(driverUser));
        return driverUserService.addDriverUser(driverUser);
    }

    /**
     * 修改司机
     *
     * @param driverUser
     * @return
     */

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        log.info(JSONObject.toJSONString(driverUser));
        return driverUserService.updateDriverUser(driverUser);
    }

    /**
     * 查询 司机
     * 如果需要按照司机的多个条件做查询，那么此处用 /user
     *
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> getUser(@PathVariable("driverPhone") String driverPhone) {

        ResponseResult<DriverUser> driverUserByPhone = driverUserService.getDriverUserByPhone(driverPhone);
        DriverUser driverUserDb = driverUserByPhone.getData();

        DriverUserExistsResponse response = new DriverUserExistsResponse();

        int ifExists = DriverCarConstants.DRIVER_EXISTS;
        if (driverUserDb == null) {
            ifExists = DriverCarConstants.DRIVER_NOT_EXISTS;
            response.setDriverPhone(driverPhone);
            response.setIfExists(ifExists);
        } else {
            response.setDriverPhone(driverUserDb.getDriverPhone());
            response.setIfExists(ifExists);
        }

        return ResponseResult.success(response);
    }


}

