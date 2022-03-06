package com.kaist.gachanono.gachanonoserver.controller;

import com.kaist.gachanono.gachanonoserver.vo.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class PageController {
    
    @GetMapping 
    public String index() {
        // Vue 예제 페이지로 이동
        return "index"; 
    } 

    @GetMapping("/test")
    public String getUser(Model model) {
        // thymeleaf 에서 사용할 데이터 전달
        User user = new User("kkaok", "테스트", "web") ;
        model.addAttribute("user", user);
        return "test";
    }

    @RequestMapping("/hello")
    public String hello(){
        // thymeleaf 에서 사용할 데이터 전달
        return "hello";
    }
}
