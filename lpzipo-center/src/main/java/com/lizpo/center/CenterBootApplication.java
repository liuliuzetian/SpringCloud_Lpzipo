package com.lizpo.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心
 */
@SpringBootApplication
@EnableEurekaServer //开启服务注册中心
public class CenterBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CenterBootApplication.class, args);
    }
}
