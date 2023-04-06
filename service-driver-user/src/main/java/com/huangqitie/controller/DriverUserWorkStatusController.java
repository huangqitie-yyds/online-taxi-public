package com.huangqitie.controller;


import com.huangqitie.internalcommon.dto.DriverUserWorkStatus;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.service.DriverUserWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄启贴
 * @since 2023-04-02
 */
@RestController

public class DriverUserWorkStatusController {

    @Autowired
    DriverUserWorkStatusService driverUserWorkStatusService;

    @PostMapping("/driver-user-work-status")
    public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus) {
        return driverUserWorkStatusService.changeWorkStatus(driverUserWorkStatus.getDriverId(),
                driverUserWorkStatus.getWorkStatus());
    }

    @GetMapping("/work-status")
    public ResponseResult<DriverUserWorkStatus> getWorkStatus(Long driverId) {
        return driverUserWorkStatusService.getWorkStatus(driverId);
    }

}
