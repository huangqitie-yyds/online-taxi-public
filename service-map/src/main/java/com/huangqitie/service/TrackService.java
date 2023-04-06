package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.TrackResponse;
import com.huangqitie.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {

    @Autowired
    TrackClient trackClient;

    public ResponseResult<TrackResponse> add(String tid){

        return trackClient.add(tid);
    }
}