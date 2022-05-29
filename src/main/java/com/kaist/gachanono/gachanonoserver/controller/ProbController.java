package com.kaist.gachanono.gachanonoserver.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller 
@RequestMapping("/prob")
public class ProbController {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/gacha") 
    public String gacha(Model model) {
        // Vue 예제 페이지로 이동

        // logger.info("user[{}]", user.toString());

        return "/prob/gacha"; 
    } 

    @GetMapping("/calculation") 
    public String calculation(Model model) {
        // Vue 예제 페이지로 이동
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        
        // logger.info("user[{}]", user.toString());

        return "/prob/calculation"; 
    } 

    @GetMapping("/report") 
    public String report(Model model) {
        // Vue 예제 페이지로 이동
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        
        // logger.info("user[{}]", user.toString());

        return "/prob/report"; 
    } 

    @GetMapping("/simulation")
    public String simulation(Model model) {
        // Vue 예제 페이지로 이동
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        
        // logger.info("user[{}]", user.toString());

        return "/prob/simulation";
    } 

    @GetMapping("/simulation2")
    public String simulation2(Model model) {
        // Vue 예제 페이지로 이동
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        
        // logger.info("user[{}]", user.toString());

        return "/prob/simulation2";
    } 

    @GetMapping("/sheetlucky")
    public String sheetlucky(Model model) {
        // Vue 예제 페이지로 이동
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        
        // logger.info("user[{}]", user.toString());

        return "/prob/sheetlucky";
    } 
    
    @GetMapping("/sheetjsce")
    public String sheetjsce(Model model) {
        // Vue 예제 페이지로 이동
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        
        // logger.info("user[{}]", user.toString());

        return "/prob/sheetjsce";
    } 
}
