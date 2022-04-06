package com.kaist.gachanono.gachanonoserver.controller;

import com.kaist.gachanono.gachanonoserver.dao.UserDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller 
public class PageController {
    
    Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/") 
    public String index() {
        logger.info("{}", userDAO.getUsers());
        // Vue 예제 페이지로 이동
        return "/index"; 
    } 

    @GetMapping("/401") 
    public String error401() {
        return "401"; 
    } 

    @GetMapping("/404") 
    public String error404() {
        return "404"; 
    }

    @GetMapping("/500") 
    public String error500() {
        return "401"; 
    }
    
    @GetMapping("/charts") 
    public String charts() {
        return "charts"; 
    }
    
    @GetMapping("/error") 
    public String error() {
        return "error"; 
    }

    // @GetMapping("/layout-sidenav-light") 
    // public String layoutSidenavLight() {
    //     return "layout-sidenav-light"; 
    // }
    
    @GetMapping("/layout-static") 
    public String layoutStatic() {
        return "layout-static"; 
    }
    
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
    
    @GetMapping("/tables") 
    public String tables() {
        return "tables"; 
    }

    @GetMapping("/test")
    public String getUser(Model model) {
        // thymeleaf 에서 사용할 데이터 전달
        //User user = new User(1122, "테스트") ;
        //model.addAttribute("user", user);
        return "test";
    }

    @RequestMapping("/hello")
    public String hello(){
        // thymeleaf 에서 사용할 데이터 전달
        return "hello";
    }
}
