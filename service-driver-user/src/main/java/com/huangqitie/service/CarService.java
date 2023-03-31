package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.Car;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.mapper.CarMapper;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
        carMapper.insert(car);
        return ResponseResult.success("");
    }
}
