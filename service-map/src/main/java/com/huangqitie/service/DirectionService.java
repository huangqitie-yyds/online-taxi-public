package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {
    /**
     * 根据起点和终点经纬度获取距离(米)和时长(分钟)
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(123);
        directionResponse.setDuration(12);
        return ResponseResult.success(directionResponse);
    }
}
