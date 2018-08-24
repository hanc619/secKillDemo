package com.hanc.seckill.seckillproducer.controller.base;

import com.hanc.seckill.seckillproducer.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: cuill
 * @date: 2018/7/26 10:32
 */

@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 通过controller层拦截异常
     *
     * @param exception  异常类型
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse errorResponse(ApiException exception) {
        return new ErrorResponse(exception.getMessage(), exception.getCode());
    }


    public class ErrorResponse {
        private String message;

        private String code;

        public ErrorResponse(String message, String code) {
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
