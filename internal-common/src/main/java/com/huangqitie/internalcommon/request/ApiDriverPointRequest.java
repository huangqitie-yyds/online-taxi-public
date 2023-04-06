package com.huangqitie.internalcommon.request;

import lombok.Data;

@Data
public class ApiDriverPointRequest {

    public Long carId;

    private PointDTO[] points;
}