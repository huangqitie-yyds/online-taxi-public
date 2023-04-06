package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.request.PointRequest;
import com.huangqitie.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    PointClient pointClient;

    public ResponseResult upload(PointRequest pointRequest){

        return pointClient.upload(pointRequest);
    }
}