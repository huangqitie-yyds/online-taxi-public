package com.huangqitie.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huangqitie.internalcommon.constant.CommonStatus;
import com.huangqitie.internalcommon.constant.MapConfigConstant;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.mapper.DicDistrictMapper;
import com.huangqitie.pojo.DicDistrict;
import com.huangqitie.remote.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    public ResponseResult initDicDistrict(String keywords) {
        // 请求地图
        String dicDistrictResult = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrictResult);
        // 解析结果
        JSONObject dicDistrictJsonObject = JSONObject.parseObject(dicDistrictResult);
        int status = dicDistrictJsonObject.getIntValue(MapConfigConstant.STATUS);
        if (status != 1) {
            return ResponseResult.fail(CommonStatus.MAP_DISTRICT_ERROR.getCode(),
                    CommonStatus.MAP_DISTRICT_ERROR.getValue());
        }
        JSONArray countryJsonArray = dicDistrictJsonObject.getJSONArray(MapConfigConstant.DISTRICTS);
        for (int country = 0; country < countryJsonArray.size(); country++) {
            JSONObject countryJsonObject = countryJsonArray.getJSONObject(country);
            String countryAddressCode = countryJsonObject.getString(MapConfigConstant.ADCODE);
            String countryAddressName = countryJsonObject.getString(MapConfigConstant.NAME);
            String countryParentAddressCode = "0";
            String countryLevel = countryJsonObject.getString(MapConfigConstant.LEVEL);

            insertDicDistrict(countryAddressCode, countryAddressName, countryLevel, countryParentAddressCode);

            JSONArray provinceJsonArray = countryJsonObject.getJSONArray(MapConfigConstant.DISTRICTS);
            for (int p = 0; p < provinceJsonArray.size(); p++) {
                JSONObject provinceJsonObject = provinceJsonArray.getJSONObject(p);
                String provinceAddressCode = provinceJsonObject.getString(MapConfigConstant.ADCODE);
                String provinceAddressName = provinceJsonObject.getString(MapConfigConstant.NAME);
                String provinceParentAddressCode = countryAddressCode;
                String provinceLevel = provinceJsonObject.getString(MapConfigConstant.LEVEL);

                insertDicDistrict(provinceAddressCode, provinceAddressName, provinceLevel, provinceParentAddressCode);

                JSONArray cityArray = provinceJsonObject.getJSONArray(MapConfigConstant.DISTRICTS);
                for (int city = 0; city < cityArray.size(); city++) {
                    JSONObject cityJsonObject = cityArray.getJSONObject(city);
                    String cityAddressCode = cityJsonObject.getString(MapConfigConstant.ADCODE);
                    String cityAddressName = cityJsonObject.getString(MapConfigConstant.NAME);
                    String cityParentAddressCode = provinceAddressCode;
                    String cityLevel = cityJsonObject.getString(MapConfigConstant.LEVEL);

                    insertDicDistrict(cityAddressCode, cityAddressName, cityLevel, cityParentAddressCode);

                    JSONArray districtArray = cityJsonObject.getJSONArray(MapConfigConstant.DISTRICTS);
                    for (int d = 0; d < districtArray.size(); d++) {
                        JSONObject districtJsonObject = districtArray.getJSONObject(d);
                        String districtAddressCode = districtJsonObject.getString(MapConfigConstant.ADCODE);
                        String districtAddressName = districtJsonObject.getString(MapConfigConstant.NAME);
                        String districtParentAddressCode = cityAddressCode;
                        String districtLevel = districtJsonObject.getString(MapConfigConstant.LEVEL);

                        if (districtLevel.equals(MapConfigConstant.STREET)) {
                            continue;
                        }

                        insertDicDistrict(districtAddressCode, districtAddressName, districtLevel,
                                districtParentAddressCode);

                    }
                }
            }

        }
        return ResponseResult.success("");
    }

    public void insertDicDistrict(String addressCode, String addressName, String level, String parentAddressCode) {
        // 数据库对象
        DicDistrict district = new DicDistrict();
        district.setAddressCode(addressCode);
        district.setAddressName(addressName);
        int levelInt = generateLevel(level);
        district.setLevel(levelInt);

        district.setParentAddressCode(parentAddressCode);

        // 插入数据库
        dicDistrictMapper.insert(district);
    }

    public int generateLevel(String level) {
        int levelInt = 0;
        if (level.trim().equals("country")) {
            levelInt = 0;
        } else if (level.trim().equals("province")) {
            levelInt = 1;
        } else if (level.trim().equals("city")) {
            levelInt = 2;
        } else if (level.trim().equals("district")) {
            levelInt = 3;
        }
        return levelInt;
    }
}
