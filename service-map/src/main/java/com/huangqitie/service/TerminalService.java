package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.TerminalResponse;
import com.huangqitie.internalcommon.response.TrsearchResponse;
import com.huangqitie.remote.TerminalClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalService {

    @Autowired
    TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> add(String name , String desc){
        return terminalClient.add(name , desc);
    }


    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius){

        return terminalClient.aroundsearch(center,radius);
    }

    public ResponseResult<TrsearchResponse> trsearch(String tid , Long starttime , Long endtime){

        return terminalClient.trsearch(tid,starttime,endtime);
    }

}