package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.remote.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFromMapService {

    @Autowired
    private ServiceClient serviceClient;


    /**
     * 创建服务
     * @param name
     * @return
     */
    public ResponseResult add(String name){

        return serviceClient.add(name);

    }
}