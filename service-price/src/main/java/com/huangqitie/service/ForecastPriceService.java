package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.PriceRule;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.request.ForecastPriceDTO;
import com.huangqitie.internalcommon.response.DirectionResponse;
import com.huangqitie.internalcommon.response.ForecastPriceResponse;
import com.huangqitie.internalcommon.util.BigDecimalUtils;
import com.huangqitie.mapper.PriceRuleMapper;
import com.huangqitie.remote.ServiceMapClient;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

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
        log.info("读取计价规则");
        Map<String, Object> map = new HashMap<>();
        map.put("city_code", 10010);
        map.put("vehicle_type", 1);
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(map);
        log.info("priceRules:" + priceRules);
        PriceRule priceRule = priceRules.get(0);
        double price = getPrice(distance, duration, priceRule);
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }

    /**
     * 根据距离、时长 和计价规则，计算最终价格
     *
     * @param distance  距离
     * @param duration  时长
     * @param priceRule 计价规则
     * @return
     */
    public double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        double price = 0;

        // 起步价
        double startFare = priceRule.getStartFare();
        price = BigDecimalUtils.add(price, startFare);

        // 里程费
        // 总里程 m
        double distanceMile = BigDecimalUtils.divide(distance, 1000);
        // 起步里程
        double startMile = (double) priceRule.getStartMile();
        double distanceSubtract = BigDecimalUtils.substract(distanceMile, startMile);
        // 最终收费的里程数 km
        double mile = distanceSubtract < 0 ? 0 : distanceSubtract;
        // 计程单价 元/km
        double unitPricePerMile = priceRule.getUnitPricePerMile();
        // 里程价格
        double mileFare = BigDecimalUtils.multiply(mile, unitPricePerMile);
        price = BigDecimalUtils.add(price, mileFare);

        // 时长费
        // 时长的分钟数
        double timeMinute = BigDecimalUtils.divide(duration, 60);
        // 计时单价
        double unitPricePerMinute = priceRule.getUnitPricePerMinute();

        // 时长费用
        double timeFare = BigDecimalUtils.multiply(timeMinute, unitPricePerMinute);
        price = BigDecimalUtils.add(price, timeFare);

        BigDecimal priceBigDecimal = new BigDecimal(price);
        priceBigDecimal = priceBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);

        return priceBigDecimal.doubleValue();
    }

}
