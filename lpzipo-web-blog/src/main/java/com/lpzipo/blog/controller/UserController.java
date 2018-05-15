package com.lpzipo.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/18 11:26
 * @Dcription TODO
 */
@RestController
public class UserController {

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String getUserInfo(@RequestParam String name){
        System.out.println(name);
        return name;
    }

}
