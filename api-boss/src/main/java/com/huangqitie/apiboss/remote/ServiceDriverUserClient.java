package com.huangqitie.apiboss.remote;

import com.huangqitie.internalcommon.dto.Car;
import com.huangqitie.internalcommon.dto.DriverCarBindingRelationship;
import com.huangqitie.internalcommon.dto.DriverUser;
import com.huangqitie.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {
    @PostMapping("/user")
    ResponseResult addDriverUser(@RequestBody DriverUser driverUser);

    @PutMapping("/user")
    ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);

    @PostMapping("/car")
    ResponseResult addCar(@RequestBody Car car);

    @PostMapping("/driver-car-binding-relationship/bind")
    ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship);

    @PostMapping("/driver-car-binding-relationship/unbind")
    ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship);
}
