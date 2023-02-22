package com.huangqitie.internalcommon.constant;

import lombok.Getter;

public enum CommonStatus {
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),
    SUCCESS(1, "success"),
    FAIL(0, "fail");
    @Getter
    public int code;
    @Getter
    public String value;

    CommonStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
