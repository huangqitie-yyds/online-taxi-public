package com.huangqitie.internalcommon.constant;

import lombok.Getter;

public enum CommonStatus {
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
