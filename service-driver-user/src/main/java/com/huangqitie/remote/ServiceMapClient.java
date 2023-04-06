package com.huangqitie.remote;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.TerminalResponse;
import com.huangqitie.internalcommon.response.TrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-map")
public interface ServiceMapClient {

    @PostMapping("/terminal/add")
    ResponseResult<TerminalResponse> addTerminal(@RequestParam String name, @RequestParam String desc);

    @PostMapping("/track/add")
    ResponseResult<TrackResponse> addTrack(@RequestParam String tid);
}