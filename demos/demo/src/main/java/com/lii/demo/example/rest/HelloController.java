package com.lii.demo.example.rest;


import com.springboot.cloud.common.core.entity.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang.RandomStringUtils.randomNumeric;

@RefreshScope
@RestController
public class HelloController {

    @Autowired
    private Environment env;

    @Value("${username}")
    private String username;

//    @Value("${sdf}")
//    private String sdf;

    @RequestMapping(method = RequestMethod.GET, value = "/hello/{name}")
    public String hello(@PathVariable String name) {
        return randomNumeric(2) + name;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public Result world(@RequestParam String name) {
        return Result.success(name + "success");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/username")
    public String userName(){
        return env.getProperty("sdf","undifend");
    }
}