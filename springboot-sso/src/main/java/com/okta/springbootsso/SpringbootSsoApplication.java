package com.okta.springbootsso;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableOAuth2Sso
@SpringBootApplication
public class SpringbootSsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootSsoApplication.class, args);
    }
    @GetMapping("/")
    public String echoTheUsersEmailAddress(Principal principal) {
        return "Hey there! Your email address is: " ;
    }
}