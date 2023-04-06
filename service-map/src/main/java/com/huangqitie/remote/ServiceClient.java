package com.huangqitie.remote;

import com.alibaba.fastjson.JSONObject;
import com.huangqitie.internalcommon.constant.MapConfigConstant;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceClient {

    @Value("${amap.key}")
    private String aMapKey;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult add(String name) {
        // &key=<用户的key>
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(MapConfigConstant.SERVICE_ADD_URL);
        url.append("?");
        url.append("key=" + aMapKey);
        url.append("&");
        url.append("name=" + name);
        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.parseObject(body);
        JSONObject data = result.getJSONObject("data");
        String sid = data.getString("sid");
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setSid(sid);
        return ResponseResult.success(serviceResponse);
    }
}