package com.kaist.gachanono.gachanonoserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller 
public class LoginController {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login") 
    public String login() {
        return "login"; 
    }
    
    @GetMapping("/password") 
    public String password() {
        return "password"; 
    }
    
    @GetMapping("/register") 
    public String register() {
        return "register"; 
    }

}
