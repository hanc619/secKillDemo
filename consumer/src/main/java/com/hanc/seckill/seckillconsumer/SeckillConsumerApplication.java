package com.hanc.seckill.seckillconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SeckillConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillConsumerApplication.class, args);
    }
}
