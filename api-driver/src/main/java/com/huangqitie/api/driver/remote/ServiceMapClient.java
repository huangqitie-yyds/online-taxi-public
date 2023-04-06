package com.huangqitie.api.driver.remote;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.request.PointRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-map")
public interface ServiceMapClient {

    @PostMapping("/point/upload")
    ResponseResult upload(@RequestBody PointRequest pointRequest);

}