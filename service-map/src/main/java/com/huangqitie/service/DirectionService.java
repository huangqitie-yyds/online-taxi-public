package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.DirectionResponse;
import com.huangqitie.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

    /**
     * 根据起点和终点经纬度获取距离(米)和时长(分钟)
     *
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        //调用第三方地图接口
        DirectionResponse direction =
                mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);
        return ResponseResult.success(direction);
    }
}
