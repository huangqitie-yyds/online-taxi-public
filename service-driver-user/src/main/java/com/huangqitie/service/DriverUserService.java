package com.huangqitie.service;

import com.huangqitie.internalcommon.constant.CommonStatus;
import com.huangqitie.internalcommon.constant.DriverCarConstants;
import com.huangqitie.internalcommon.dto.DriverUser;
import com.huangqitie.internalcommon.dto.DriverUserWorkStatus;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.mapper.DriverUserMapper;
import com.huangqitie.mapper.DriverUserWorkStatusMapper;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        driverUserMapper.insert(driverUser);
        // 初始化 司机工作状态表
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);
        return ResponseResult.success("");
    }

    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success("");
    }
    public ResponseResult<DriverUser> getDriverUserByPhone(String driverPhone){
        Map<String,Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverCarConstants.DRIVER_STATE_VALID);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
        if (driverUsers.isEmpty()){
            return ResponseResult.fail(CommonStatus.DRIVER_NOT_EXITST.getCode(),CommonStatus.DRIVER_NOT_EXITST.getValue());
        }
        DriverUser driverUser = driverUsers.get(0);
        return ResponseResult.success(driverUser);
    }

}
