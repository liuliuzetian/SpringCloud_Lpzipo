package com.lpzipo.client.feign.service;

import com.lpzipo.client.feign.pojo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("lpzipo-service")//调用服务
public interface HelloService {

    @RequestMapping("/sayhi")
    String hello();

    /**
     * 不能缺失 value属性
     * @param name
     * @return
     */
    @RequestMapping(value = "sayhi1", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "sayhi2", method = RequestMethod.GET)
    User hello(@RequestHeader("name") String name,@RequestHeader("age") Integer age);

    @RequestMapping(value = "sayhi3", method = RequestMethod.POST)
    String hello(@RequestBody User user);


}
