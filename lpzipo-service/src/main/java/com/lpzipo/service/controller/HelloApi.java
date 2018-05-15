package com.lpzipo.service.controller;

import com.lpzipo.service.pojo.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloApi {

    @RequestMapping(value = "/sayhi", method = RequestMethod.GET)
    public String sayHello(){
        return "Hello World 1";
    }

    @RequestMapping(value = "/sayhi1", method = RequestMethod.GET)
    public String sayHello(@RequestParam("name") String name){
        return "Hello World 2"+name;
    }

    @RequestMapping(value = "/sayhi2", method = RequestMethod.GET)
    public User sayHello2(@RequestHeader String name, @RequestHeader Integer age){
        return new User(name,age);
    }

    @RequestMapping(value = "/sayhi3", method = RequestMethod.POST)
    public String sayHello3(@RequestBody User user){

        return"Hello "+user.getName()+","+user.getAge();
    }

    @RequestMapping(value = "/hello-zuul", method = RequestMethod.GET)
    public String helloZuul(){
        return "return from lpzipo-service";
    }

}
