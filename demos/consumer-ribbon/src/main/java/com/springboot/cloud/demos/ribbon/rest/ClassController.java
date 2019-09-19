package com.springboot.cloud.demos.ribbon.rest;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.demos.ribbon.service.ClassService;
import com.springboot.cloud.demos.ribbon.service.HelloToDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
public class ClassController {

    @Autowired
    ClassService classService;

    @Autowired
    HelloToDemoService helloToDemoService;

    @GetMapping("/classes")
    public Result hello(@RequestParam String name) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Result users = classService.users(name);
        context.close();
        return users;
    }

    @PostMapping("/classes")
    public Result hello(@RequestBody Map<String, String> params) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Result users = classService.users(params);
        context.close();
        return users;
    }

    @GetMapping("/class/{userId}")
    public Result hello(@PathVariable("userId") long id) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Result user = classService.search(id);
        context.close();
        return user;
    }

    @RequestMapping(value = "/testToDemo",method = RequestMethod.GET)
    public Result testToDemo(String hello){
        log.info("testToDemo message:{}"+hello);
        return Result.success(hello);
    }

//    @GetMapping
//    public Result testToDemo2(String hello2){
//        log.info("testToDemo2 message:{}"+hello2);
//        return Result.success(helloToDemoService.helloToDemo(hello2));
//    }
}
