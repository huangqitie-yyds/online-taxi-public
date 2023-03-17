package com.huangqitie.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huangqitie.internalcommon.constant.MapConfigConstant;
import com.huangqitie.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String aMapKey;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude,
                                       String destLatitude) {
        //组装请求URL
        //https://restapi.amap.com/v3/direction/driving?origin=116.481028,39.989643&destination=116.465302,40.004717&extensions=all&output=json&key=364c59fda5ef4107ab4e1aaf8d0c1161
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(MapConfigConstant.DIRECTION_URL);
        urlBuilder.append("?");
        urlBuilder.append("origin=" + depLongitude + "," + depLatitude);
        urlBuilder.append("&");
        urlBuilder.append("destination=" + destLongitude + "," + destLatitude);
        urlBuilder.append("&");
        urlBuilder.append("extensions=all");
        urlBuilder.append("&");
        urlBuilder.append("output=json");
        urlBuilder.append("&");
        urlBuilder.append("key=" + aMapKey);
        log.info(urlBuilder.toString());
        //调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        String directionBody = directionEntity.getBody();
        log.info("高德地图返回结果信息:" + directionBody);
        //解析返回数据
        DirectionResponse directionResponse = parseDirectionBody(directionBody);
        return directionResponse;
    }

    private DirectionResponse parseDirectionBody(String directionBody) {
        DirectionResponse directionResponse = null;
        try {

            JSONObject result = JSONObject.parseObject(directionBody);
            if (result.containsKey(MapConfigConstant.STATUS)) {
                int status = result.getIntValue(MapConfigConstant.STATUS);
                if (status == 1) {
                    if (result.containsKey(MapConfigConstant.ROUTE)) {
                        JSONObject routeObject = result.getJSONObject(MapConfigConstant.ROUTE);
                        JSONArray pathsArray = routeObject.getJSONArray(MapConfigConstant.PATHS);
                        JSONObject pathObject = pathsArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                        if (pathObject.containsKey(MapConfigConstant.DISTANCE)) {
                            int distance = pathObject.getIntValue(MapConfigConstant.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if (pathObject.containsKey(MapConfigConstant.DURATION)) {
                            int duration = pathObject.getIntValue(MapConfigConstant.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }

        } catch (Exception e) {

        }
        return directionResponse;
    }
}
