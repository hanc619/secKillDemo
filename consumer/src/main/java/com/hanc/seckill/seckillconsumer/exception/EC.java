package com.hanc.seckill.seckillconsumer.exception;

/**
 * Error Code
 */
public enum EC {

    /**
     * 成功
     */
    SUCCESS("20000", "Success"),
    /**
     * 缺少必选参数
     */
    MISS_PARAMETER("40001", "Miss Parameter"),
    /**
     * 非法参数
     */
    ILLEGAL_PARAMETER("40002", "Illegal Parameter"),

    /**
     * 业务处理失败
     */
    FAILED_BUSINESS("40004", "Business Failed");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码描述
     */
    private String msg;

    EC(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
}
