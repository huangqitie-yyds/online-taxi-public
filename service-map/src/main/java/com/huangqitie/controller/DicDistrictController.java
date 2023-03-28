package com.huangqitie.controller;


import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hqt
 * @since 2023-03-21
 */
@RestController
public class DicDistrictController {

    @Autowired
    private DicDistrictService dicDistrictService;

    @GetMapping("/dic-district")
    public ResponseResult initDistrict(String keywords) {
        return dicDistrictService.initDicDistrict(keywords);
    }

}

