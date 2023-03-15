package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude,
                                        String destLatitude) {
        log.info("起点经度:" + depLongitude);
        log.info("起点纬度:" + depLatitude);
        log.info("终点经度:" + destLongitude);
        log.info("终点纬度:" + destLatitude);
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.345);
        return ResponseResult.success(forecastPriceResponse);
    }
}
