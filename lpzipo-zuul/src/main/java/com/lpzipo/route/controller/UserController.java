package com.lpzipo.route.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/19 14:28
 * @Dcription TODO
 */
@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String gerInfo (){
        return "Hello World";
    }
}
