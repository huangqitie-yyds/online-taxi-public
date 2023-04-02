package com.huangqitie.api.driver.remote;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-verificationcode")
public interface ServiceVerificationCodeClient {

    @GetMapping("/numberCode/{size}")
    ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size") int size);

}
