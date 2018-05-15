package com.lpzipo.service.controller;

import com.lpzipo.api.dto.User;
import com.lpzipo.api.service.HelloService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现公共api接口
 */
@RestController
public class RefactorHelloApi implements HelloService {

    @Override
    public String hello(String name) {
        return "hello" +name;
    }

    @Override
    public User hello(String name, Integer age) {
        return new User(name,age);
    }

    @Override
    public String hello(User user) {
        return user.getName()+"---"+user.getAge();
    }
}
