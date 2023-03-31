package com.huangqitie.controller;


import com.huangqitie.internalcommon.dto.Car;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 黄启贴
 * @since 2023-03-30
 */
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }


}
