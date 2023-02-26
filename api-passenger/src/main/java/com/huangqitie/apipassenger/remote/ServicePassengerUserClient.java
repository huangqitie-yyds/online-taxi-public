package com.huangqitie.apipassenger.remote;

import com.huangqitie.internalcommon.constant.dto.ResponseResult;
import com.huangqitie.internalcommon.constant.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @PostMapping("/user")
    ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);
}
