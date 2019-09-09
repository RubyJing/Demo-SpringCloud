package com.lii.demo.example.service.impl;

import com.lii.demo.example.service.HelloToRibbonService;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.springboot.cloud.common.core.entity.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author RubyJing
 * @Date 2019/9/6 10:39
 * @Version 1.0
 */
@Service
public class HelloToRibbonServiceImpl implements HelloToRibbonService {

    @Autowired
    RestTemplate restTemplate;

    @CacheResult
    @Override
    public Result helloToRibbon(String hello) {
        return restTemplate.getForObject("http://RIBBON-CONSUMER/testToDemo?hello={1}", Result.class, hello);
    }
}
