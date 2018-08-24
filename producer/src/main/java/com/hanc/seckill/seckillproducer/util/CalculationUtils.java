package com.hanc.seckill.seckillproducer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;

/**
 * 数据计算相关类
 */
public class CalculationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationUtils.class);

    /**
     * 百分比基数
     */
    private static final int PERCENTAGE_NUMBER = 100;

    /**
     * 根据两个long型的数值来获取百分比值
     *
     * @param dividend        被除数
     * @param divisor         除数
     * @param retentionNumber 保留位数
     * @return
     */
    public static String countConversionRateDate(long dividend, long divisor, int retentionNumber) {

        if (divisor <= 0) {
            LOGGER.info("countConversionRateDate is invalid, " +
                    "dividend is [{}], divisor is [{}]", dividend, divisor);
            return "";
        }

        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后的位数
        numberFormat.setMaximumFractionDigits(retentionNumber);

        String result = numberFormat.format((float) dividend / (float) divisor * PERCENTAGE_NUMBER);

        return result + "%";
    }
}
