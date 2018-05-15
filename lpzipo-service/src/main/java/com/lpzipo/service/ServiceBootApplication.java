package com.lpzipo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceBootApplication.class,args);
    }
}
