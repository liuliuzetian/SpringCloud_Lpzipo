package com.lpzipo.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

/**
 * 服务端负载均衡
 */
@EnableZuulServer
@EnableZuulProxy
@SpringBootApplication
public class RoueteRootApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoueteRootApplication.class, args);
    }

}
