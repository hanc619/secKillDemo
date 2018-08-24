package com.hanc.seckill.seckillproducer.controller;

import com.hanc.seckill.seckillproducer.common.MarketingResponse;
import com.hanc.seckill.seckillproducer.service.StockOrderService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: cuill
 * @date: 2018/8/16 9:59
 */
@RestController
@RequestMapping(value = "stock")
@Api(value = "订单", description = "订单")
public class StockOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderController.class);

    @Autowired
    private StockOrderService stockOrderService;


    /**
     * 获取广告点击浏览的总数
     *
     * @return
     */
    @PostMapping("/order")
    public MarketingResponse order(int sid) {
        stockOrderService.createOrder(sid);
        return MarketingResponse.success();
    }
}
