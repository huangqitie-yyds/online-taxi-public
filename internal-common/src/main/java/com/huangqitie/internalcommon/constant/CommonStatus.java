package com.huangqitie.internalcommon.constant;

import lombok.Getter;

public enum CommonStatus {
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),
    TOKEN_ERROR(1199, "令牌不正确"),
    USER_NOT_EXISTS(1200, "手机号不存在"),
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
