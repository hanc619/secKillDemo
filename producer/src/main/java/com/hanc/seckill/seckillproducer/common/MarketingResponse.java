package com.hanc.seckill.seckillproducer.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanc.seckill.seckillproducer.exception.EC;

/**
 * @author cuill
 * 返回给前端的数据
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public final class MarketingResponse {

    /**
     * 错误码或者失败码
     */
    private String code;

    /**
     * 错误信息或者成功信息
     */
    private String msg;

    /**
     * 返回的数据值
    */
     private Object data;

    private MarketingResponse() {
    }

    /**
     * 创建一个构造器
     * @return
     */
    public static MarketingResponse builder() {
        return new MarketingResponse();
    }

    /**
     * 成功返回
     *
     * @return
     */
    public static MarketingResponse success() {
        return builder().setCode(EC.SUCCESS.getCode()).setMsg(EC.SUCCESS.getMsg());
    }

    /**
     * 成功返回
     *
     * @param data 返回的数据
     * @return
     */
    public static MarketingResponse success(Object data) {
        return success().setData(data);
    }

    /**
     * 参数缺失
     *
     * @return
     */
    public static MarketingResponse miss() {
        return builder()
                .setCode(EC.MISS_PARAMETER.getCode())
                .setMsg(EC.MISS_PARAMETER.getMsg());
    }

    /**
     * 参数缺失
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return
     */
    public static MarketingResponse miss(String code, String msg) {
        return builder()
                .setCode(code)
                .setMsg(msg);
    }

    /**
     * 参数不合法
     *
     * @return
     */
    public static MarketingResponse illegal() {
        return builder()
                .setCode(EC.ILLEGAL_PARAMETER.getCode())
                .setMsg(EC.ILLEGAL_PARAMETER.getMsg());
    }

    /**
     * 业务处理失败
     *
     * @return
     */
    public static MarketingResponse fail() {
        return builder()
                .setCode(EC.FAILED_BUSINESS.getCode())
                .setMsg(EC.FAILED_BUSINESS.getMsg());
    }

    public String getCode() {
        return code;
    }

    public MarketingResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public MarketingResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public MarketingResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
