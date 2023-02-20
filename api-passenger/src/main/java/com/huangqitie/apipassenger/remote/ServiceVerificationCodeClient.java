package com.huangqitie.apipassenger.remote;

import com.huangqitie.internalcommon.constant.dto.ResponseResult;
import com.huangqitie.internalcommon.constant.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("service-verificationcode")
public interface ServiceVerificationCodeClient {
    @GetMapping("/numberCode/{size}")
    ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size") int size);
}
