package com.hanc.seckill.seckillconsumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.hanc.seckill.*.dao"})
public class SeckillConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillConsumerApplication.class, args);
    }
}
