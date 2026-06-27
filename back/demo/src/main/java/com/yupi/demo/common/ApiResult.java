package com.yupi.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResult<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.code = 200;
        result.msg = "操作成功";
        result.data = data;
        return result;
    }

    public static <T> ApiResult<T> fail(int code, String msg) {
        ApiResult<T> result = new ApiResult<>();
        result.code = code;
        result.msg = msg;
        result.data = null;
        return result;
    }

    public static <T> ApiResult<T> badRequest(String msg) {
        return fail(400, msg);
    }

    public static <T> ApiResult<T> serverError(String msg) {
        return fail(500, msg);
    }
}
