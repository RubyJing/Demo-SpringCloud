package com.springboot.auth.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class Oauth2AuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthorizationApplication.class, args);
    }
}
