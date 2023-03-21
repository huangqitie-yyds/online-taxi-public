package com.huangqitie.apipassenger.service;

import com.huangqitie.apipassenger.remote.ServicePriceClient;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.request.ForecastPriceDTO;
import com.huangqitie.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {

    @Autowired
    private ServicePriceClient servicePriceClient;

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
        ResponseResult<ForecastPriceResponse> responseResult = servicePriceClient.forecastPrice(forecastPriceDTO);
        Double price = responseResult.getData().getPrice();
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }
}
