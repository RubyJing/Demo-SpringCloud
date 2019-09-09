package com.lii.demo.example.rest;

import com.lii.demo.example.service.HelloToRibbonService;
import com.springboot.cloud.common.core.entity.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang.RandomStringUtils.randomNumeric;

@RestController
public class HelloController {

    @Autowired
    private HelloToRibbonService helloToRibbonService;

    @RequestMapping(method = RequestMethod.GET, value = "/hello/{name}")
    public String hello(@PathVariable String name) {
        return randomNumeric(2) + name;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public Result world(@RequestParam String name) {
        return Result.success(name + "success");
    }

    @GetMapping
    public Result HelloToRibbonService(String hello){
      return  Result.success(helloToRibbonService.helloToRibbon(hello)) ;
    }
}