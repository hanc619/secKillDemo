package com.hanc.seckill.seckillconsumer.listener;

import com.google.gson.Gson;
import com.hanc.mq.core.consumer.OnsSubscriber;
import com.hanc.mq.core.consumer.base.Observer;
import com.hanc.seckill.seckillconsumer.entity.OnsTopic;
import com.hanc.seckill.seckillconsumer.entity.Stock;
import com.hanc.seckill.seckillconsumer.service.StockService;
import com.hanc.seckill.seckillconsumer.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OrderMessageListenEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMessageListenEvent.class);

    @Autowired
    private OnsSubscriber subscriber;

    @Autowired
    private OnsTopic onsTopic;

    @Autowired
    private StockService stockService;

    private Gson gson;

    public OrderMessageListenEvent() {
        this.gson = new Gson();
    }

    @PostConstruct
    public void orderMsgConsumers() {
        if (!onsTopic.getOnsSwitch()) {
            LOGGER.info("the onsSwitch2 is set off, consumer not subscribe.");
            return;
        }
        subscriber.attach(onsTopic.getMsgTopic(), "stockMessage || placeOrder", new Observer() {
            @Override
            public void onMessage(String message, String tag) {
                if (tag.equals("stockMessage")) {
                    LOGGER.info("get sensitive2 ONS Msg id is [{}]：", message);
                    stockService.updateMessageToRedis(Integer.parseInt(message));
                } else if (tag.equals("placeOrder")) {
                    Stock stock = gson.fromJson(message, Stock.class);
                    LOGGER.info("get sensitive2 ONS Msg stock is [{}]：", JsonUtil.toString(stock));
                    stockService.updateStockOptimisticByRedis(stock);
                }
            }
        });
    }
}
