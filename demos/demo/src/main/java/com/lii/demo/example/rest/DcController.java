package com.lii.demo.example.rest;

import com.lii.demo.example.entity.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Slf4j
@RestController
public class DcController {

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello3", method = RequestMethod.GET)
    public String hello() throws Exception {
        //让处理线程等待几秒钟
        int sleepTime = new Random().nextInt(3000);
        log.info("sleepTime:" + sleepTime);
        Thread.sleep(sleepTime);
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }

    @RequestMapping(value = "/hello4", method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

//    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
//    public User hello(@RequestHeader long id,
//                      @RequestHeader String name,
//                      @RequestHeader String password) {
//        return new User(id, name, password);
//    }

    @RequestMapping(value = "/hello5", method = RequestMethod.POST)
    public String hello(@RequestBody User user) {
        return "Hello " + user.getId() + ", " + user.getUserName() + ", " + user.getPassWord();
    }

}
