package com.kaist.gachanono.gachanonoserver.controller.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller 
public class PageController {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

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
}
