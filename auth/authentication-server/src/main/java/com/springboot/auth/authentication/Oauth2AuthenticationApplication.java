package com.springboot.auth.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringCloudApplication
public class Oauth2AuthenticationApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthenticationApplication.class, args);
    }
}
