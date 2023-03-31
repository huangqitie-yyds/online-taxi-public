package com.huangqitie.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangqitie.internalcommon.constant.CommonStatus;
import com.huangqitie.internalcommon.constant.DriverCarConstants;
import com.huangqitie.internalcommon.dto.DriverCarBindingRelationship;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.mapper.DriverCarBindingRelationshipMapper;
import com.huangqitie.mapper.DriverUserMapper;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverCarBindingRelationshipService {
    @Autowired
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    @Autowired
    DriverUserMapper driverUserMapper;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship){
        // 判断，如果参数中的车辆和司机，已经做过绑定，则不允许再次绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        Integer integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if ((integer.intValue() > 0)){
            return ResponseResult.fail(CommonStatus.DRIVER_CAR_BIND_EXISTS.getCode(),CommonStatus.DRIVER_CAR_BIND_EXISTS.getValue());
        }

        // 司机被绑定了
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);
        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if ((integer.intValue() > 0)){
            return ResponseResult.fail(CommonStatus.DRIVER_BIND_EXISTS.getCode(),CommonStatus.DRIVER_BIND_EXISTS.getValue());
        }

        // 车辆被绑定了
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);
        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if ((integer.intValue() > 0)){
            return ResponseResult.fail(CommonStatus.CAR_BIND_EXISTS.getCode(),CommonStatus.CAR_BIND_EXISTS.getValue());
        }

        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);

        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);

        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("");

    }


    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship){
        LocalDateTime now = LocalDateTime.now();

        Map<String,Object> map = new HashMap<>();
        map.put("driver_id",driverCarBindingRelationship.getDriverId());
        map.put("car_id",driverCarBindingRelationship.getCarId());
        map.put("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(map);
        if (driverCarBindingRelationships.isEmpty()){
            return ResponseResult.fail(CommonStatus.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),CommonStatus.DRIVER_CAR_BIND_NOT_EXISTS.getValue());
        }



        DriverCarBindingRelationship relationship = driverCarBindingRelationships.get(0);
        relationship.setBindState(DriverCarConstants.DRIVER_CAR_UNBIND);
        relationship.setUnBindingTime(now);

        driverCarBindingRelationshipMapper.updateById(relationship);
        return ResponseResult.success("");

    }

}
