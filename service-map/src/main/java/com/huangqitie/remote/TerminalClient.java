package com.huangqitie.remote;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huangqitie.internalcommon.constant.MapConfigConstant;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.TerminalResponse;
import com.huangqitie.internalcommon.response.TrsearchResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TerminalClient {

    @Value("${amap.key}")
    private String aMapKey;

    @Value("${amap.sid}")
    private String aMapSid;

    @Autowired
    private RestTemplate restTemplate;


    public ResponseResult<TerminalResponse> add(String name , String desc){

        // &key=<用户的key>
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(MapConfigConstant.TERMINAL_ADD);
        url.append("?");
        url.append("key="+aMapKey);
        url.append("&");
        url.append("sid="+aMapSid);
        url.append("&");
        url.append("name="+name);
        url.append("&");
        url.append("desc="+desc);
        System.out.println("创建终端请求："+url.toString());
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        System.out.println("创建终端响应："+stringResponseEntity.getBody());
        /**
         * {
         *     "data": {
         *         "name": "车辆2",
         *         "tid": 583145283,
         *         "sid": 797498
         *     },
         *     "errcode": 10000,
         *     "errdetail": null,
         *     "errmsg": "OK"
         * }
         */
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.parseObject(body);
        JSONObject data = result.getJSONObject("data");
        String tid = data.getString("tid");

        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);

        return  ResponseResult.success(terminalResponse);
    }


    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius){
        StringBuilder url = new StringBuilder();
        url.append(MapConfigConstant.TERMINAL_AROUNDSEARCH);
        url.append("?");
        url.append("key="+aMapKey);
        url.append("&");
        url.append("sid="+aMapSid);
        url.append("&");
        url.append("center="+center);
        url.append("&");
        url.append("radius="+radius);

        System.out.println("终端搜索请求："+url.toString());
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        System.out.println("终端搜索响应："+stringResponseEntity.getBody());

        // 解析终端搜索结果
        String body = stringResponseEntity.getBody();
        JSONObject result = JSONObject.parseObject(body);
        JSONObject data = result.getJSONObject("data");

        List<TerminalResponse> terminalResponseList = new ArrayList<>();

        JSONArray results = data.getJSONArray("results");
        for (int i=0;i<results.size();i++){
            TerminalResponse terminalResponse = new TerminalResponse();

            JSONObject jsonObject = results.getJSONObject(i);
            // desc是carId，
            String desc = jsonObject.getString("desc");
            Long carId = Long.parseLong(desc);
            String tid = jsonObject.getString("tid");

            JSONObject location = jsonObject.getJSONObject("location");
            String longitude = location.getString("longitude");
            String latitude = location.getString("latitude");

            terminalResponse.setCarId(carId);
            terminalResponse.setTid(tid);
            terminalResponse.setLongitude(longitude);
            terminalResponse.setLatitude(latitude);

            terminalResponseList.add(terminalResponse);
        }



        return ResponseResult.success(terminalResponseList);
    }

    public ResponseResult<TrsearchResponse> trsearch(String tid, Long starttime , Long endtime){
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(MapConfigConstant.TERMINAL_TRSEARCH);
        url.append("?");
        url.append("key="+aMapKey);
        url.append("&");
        url.append("sid="+aMapSid);
        url.append("&");
        url.append("tid="+tid);
        url.append("&");
        url.append("starttime="+starttime);
        url.append("&");
        url.append("endtime="+endtime);

        System.out.println("高德地图查询轨迹结果请求："+url.toString());
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);
        System.out.println("高德地图查询轨迹结果响应："+forEntity.getBody());

        JSONObject result = JSONObject.parseObject(forEntity.getBody());
        JSONObject data = result.getJSONObject("data");
        int counts = data.getIntValue("counts");
        if (counts == 0){
            return null;
        }
        JSONArray tracks = data.getJSONArray("tracks");
        long driveMile = 0L;
        long driveTime = 0L;
        for (int i=0;i<tracks.size();i++){
            JSONObject jsonObject = tracks.getJSONObject(i);

            long distance = jsonObject.getLong("distance");
            driveMile = driveMile + distance;

            long time = jsonObject.getLong("time");
            time = time / (1000 * 60);
            driveTime = driveTime + time;
        }
        TrsearchResponse trsearchResponse = new TrsearchResponse();
        trsearchResponse.setDriveMile(driveMile);
        trsearchResponse.setDriveTime(driveTime);
        return ResponseResult.success(trsearchResponse);

    }
}