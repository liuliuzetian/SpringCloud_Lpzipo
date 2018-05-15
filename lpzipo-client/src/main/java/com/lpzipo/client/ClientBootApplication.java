package com.lpzipo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients //开启spring feign 功能
@EnableDiscoveryClient //发现服务 否则无法发现在注册中心注册的服务 页无法被注册
@SpringBootApplication
public class ClientBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientBootApplication.class,args);
    }
}
