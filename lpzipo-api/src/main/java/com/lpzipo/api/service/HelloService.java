package com.lpzipo.api.service;

import com.lpzipo.api.dto.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/refactor")
public interface HelloService {

    @RequestMapping(value = "/sayhi4", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/sayhi5", method = RequestMethod.GET)
    User hello(@RequestHeader("name") String name,@RequestHeader("age") Integer age);

    @RequestMapping(value = "/sayhi6", method = RequestMethod.POST)
    String hello(@RequestBody User user);

}
