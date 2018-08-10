package com.hanc.seckill.seckillproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SeckillproducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillproducerApplication.class, args);
    }
}
