package com.okta.springbootsso.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {
	
	@GetMapping("/log")
    public String echoTheUsersEmailAddress(Principal principal) {
        return "Hey there! Your email address is: " + principal.getName();
    }

   
}