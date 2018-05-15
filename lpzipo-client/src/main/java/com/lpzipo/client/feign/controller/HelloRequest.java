package com.lpzipo.client.feign.controller;

import com.lpzipo.client.feign.pojo.User;
import com.lpzipo.client.feign.service.HelloService;
import com.lpzipo.client.feign.service.RefactHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRequest {

    @Autowired
    HelloService helloService;

    @Autowired
    RefactHelloService refactHelloService;

    @RequestMapping(value = "/hello-zuul", method = RequestMethod.GET)
    public String helloZuul(){
        return "return from lpzipo-client";
    }

    @RequestMapping(value = "/hello-feign", method = RequestMethod.GET)
    public String helloConsumer(){
       return helloService.hello();
    }

    @RequestMapping(value = "/hello-feign2", method = RequestMethod.GET)
    public  String helloConsumer2(){
        StringBuilder builder = new StringBuilder();
        builder.append(helloService.hello()).append("\r\n")
                .append(helloService.hello("liuzetian")).append("\r\n")
                .append(helloService.hello("liuzetian",25).getName()).append("\r\n")
                .append(helloService.hello(new User("liu",30))).append("\r\n");
        return builder.toString();
    }


    /**
     * 继承特性  调用方继承 公共api接口
     *           服务方实现公共api接口 并添加@RestController 暴露接口
     * @return
     */
    @RequestMapping(value = "/hello-feign3", method = RequestMethod.GET)
    public  String helloConsumer3(){
        StringBuilder builder = new StringBuilder();
        builder.append(refactHelloService.hello("liuzetian")).append("\r\n")
                .append(refactHelloService.hello("liuzetian",25).getName()).append("\r\n")
                .append(refactHelloService.hello(new com.lpzipo.api.dto.User("liu",30))).append("\r\n");
        return builder.toString();
    }


}
