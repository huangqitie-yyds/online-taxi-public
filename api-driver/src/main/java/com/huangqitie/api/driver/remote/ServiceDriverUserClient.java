package com.huangqitie.api.driver.remote;

import com.huangqitie.internalcommon.dto.Car;
import com.huangqitie.internalcommon.dto.DriverUser;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.DriverUserExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @PutMapping("/user")
    ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);

    @GetMapping("/check-driver/{driverPhone}")
    ResponseResult<DriverUserExistsResponse> checkDriver(@PathVariable("driverPhone") String driverPhone);

    @GetMapping("/car")
    ResponseResult<Car> getCarById(@RequestParam Long carId);

//    @PostMapping("/driver-user-work-status")
//    public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus);
//
//    @GetMapping("/driver-car-binding-relationship")
//    public ResponseResult<DriverCarBindingRelationship> getDriverCarRelationShip(@RequestParam String driverPhone);
//
//    @GetMapping("/work-status")
//    public ResponseResult<DriverUserWorkStatus> getWorkStatus(@RequestParam Long driverId);
}
