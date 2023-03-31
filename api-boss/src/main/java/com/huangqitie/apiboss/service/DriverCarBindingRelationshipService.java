package com.huangqitie.apiboss.service;

import com.huangqitie.apiboss.remote.ServiceDriverUserClient;
import com.huangqitie.internalcommon.dto.DriverCarBindingRelationship;
import com.huangqitie.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;


    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship){
        return serviceDriverUserClient.bind(driverCarBindingRelationship);
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {

        return serviceDriverUserClient.unbind(driverCarBindingRelationship);
    }
}
