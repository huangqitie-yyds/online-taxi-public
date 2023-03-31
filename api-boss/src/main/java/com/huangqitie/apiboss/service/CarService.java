package com.huangqitie.apiboss.service;

import com.huangqitie.apiboss.remote.ServiceDriverUserClient;
import com.huangqitie.internalcommon.dto.Car;
import com.huangqitie.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addCar(Car car) {
        return serviceDriverUserClient.addCar(car);
    }
}
