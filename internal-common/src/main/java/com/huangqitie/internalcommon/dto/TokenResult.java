package com.huangqitie.internalcommon.dto;

import lombok.Data;

@Data
public class TokenResult {
    //用户手机号
    private String phone;
    //用户身份 1：乘客 2：司机
    private String identity;
}
