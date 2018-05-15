package com.lpzipo.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/17 10:59
 * @Dcription TODO
 */
//@Configuration
//@EnableAutoConfiguration //让 SpringBoot 根据应用所声明的依赖来对 Spring 框架进行自动配置，由于spring-boot-starter-web 添加了Tomcat和Spring MVC，所以auto-configuration将假定你正在开发一个web应用并相应地对Spring进行设置
//@ComponentScan //示将该类自动发现（扫描）并注册为Bean，可以自动收集所有的Spring组件（@Component , @Service , @Repository , @Controller 等），包括@Configuration类。
@EnableTransactionManagement//启用注解事物
@SpringBootApplication //@EnableAutoConfiguration、@ComponentScan和@Configuration的合集。
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
