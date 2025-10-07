package com.mashibing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.mashibing.mapper")
public class UserPointsStarterAPP {
    public static void main(String[] args) {
        SpringApplication.run(UserPointsStarterAPP.class,args);
    }
}
