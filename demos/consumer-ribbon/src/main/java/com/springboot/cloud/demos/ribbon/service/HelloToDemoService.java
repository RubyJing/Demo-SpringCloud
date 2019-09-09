package com.springboot.cloud.demos.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.common.core.exception.SystemErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author RubyJing
 * @Date 2019/9/6 11:24
 * @Version 1.0
 */
@Service
public class HelloToDemoService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloToDemoback")
    public Result helloToDemo(String name) {
        return restTemplate.getForEntity("http://USER/hello/name={1}", Result.class, name).getBody();
    }

    public Result helloToDemoback(String name) {
        return Result.fail(SystemErrorType.SYSTEM_BUSY);
    }
}
