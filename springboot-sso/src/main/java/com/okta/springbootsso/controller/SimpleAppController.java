package com.okta.springbootsso.controller;


import java.security.Principal;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RestController
//@EnableOAuth2Sso
//@RequestMapping("/myapp")
public class SimpleAppController {
    
   
    @GetMapping("/sign")
    public String echoTheUsersEmailAddress(Principal principal) {
        return "Hey there! Your email address is: " + principal.getName();
    }

}