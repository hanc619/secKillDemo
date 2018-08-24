package com.hanc.seckill.seckillconsumer.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: cuill
 * @date: 2018/8/7 11:32
 */
public class ApiException extends RuntimeException {

    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误码
     */
    private String code;

    public ApiException(String code, String message) {
        this.message = message;
        this.code = code;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ApiException unauthorized(String message) {
        return new ApiException(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), message);
    }


}
