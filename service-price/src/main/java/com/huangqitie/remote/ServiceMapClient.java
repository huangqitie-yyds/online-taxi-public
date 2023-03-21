package com.huangqitie.remote;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.request.ForecastPriceDTO;
import com.huangqitie.internalcommon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-map")
public interface ServiceMapClient {

    @GetMapping("/direction/driving")
    ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
