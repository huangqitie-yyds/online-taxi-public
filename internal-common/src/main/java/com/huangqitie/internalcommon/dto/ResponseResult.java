package com.huangqitie.internalcommon.dto;

import com.huangqitie.internalcommon.constant.CommonStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;

    /**
     * 成功响应的方法
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success() {
        return new ResponseResult().setCode(CommonStatus.SUCCESS.getCode()).setMessage(CommonStatus.SUCCESS.getValue());
    }


    /**
     * 成功响应的方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(CommonStatus.SUCCESS.getCode()).setMessage(CommonStatus.SUCCESS.getValue())
                .setData(data);
    }

    /**
     * 失败：统一的失败
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult fail(T data) {
        return new ResponseResult().setData(data);
    }

    /**
     * 失败 自定义错误码和提示信息
     *
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult fail(int code, String message) {
        return new ResponseResult().setCode(code).setMessage(message);
    }

    /**
     * 失败 自定义错误码、提示信息和返回数据
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseResult fail(int code, String message, String data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }
}
