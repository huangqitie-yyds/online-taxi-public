package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.request.ForecastPriceDTO;
import com.huangqitie.internalcommon.response.DirectionResponse;
import com.huangqitie.internalcommon.response.ForecastPriceResponse;
import com.huangqitie.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude,
                                        String destLatitude) {
        log.info("起点经度:" + depLongitude);
        log.info("起点纬度:" + depLatitude);
        log.info("终点经度:" + destLongitude);
        log.info("终点纬度:" + destLatitude);
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        ResponseResult<DirectionResponse> direction = serviceMapClient.driving(forecastPriceDTO);
        Integer distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();
        log.info("距离是:" + distance + ",时长是:" + duration);
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.345);
        return ResponseResult.success(forecastPriceResponse);
    }
}
