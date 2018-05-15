package com.lpzipo.client.feign.service;

import com.lpzipo.api.dto.User;
import com.lpzipo.api.service.HelloService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 继承公共api接口
 */
@FeignClient(value = "lpzipo-service")
public interface RefactHelloService extends HelloService {

}
