package com.kaist.gachanono.gachanonoserver.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaist.gachanono.gachanonoserver.config.auth.LoginUser;
import com.kaist.gachanono.gachanonoserver.domain.Game.Gacha;
import com.kaist.gachanono.gachanonoserver.domain.Game.Game;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;
import com.kaist.gachanono.gachanonoserver.service.GachaService;
import com.kaist.gachanono.gachanonoserver.service.GameService;

import lombok.RequiredArgsConstructor;


@Controller 
@RequiredArgsConstructor
@RequestMapping("/prob")
public class ProbController {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession httpSession;

    private final GameService gameService;
    private final GachaService gachaService;

    @GetMapping("/gacha") 
    public String gacha(Model model,
    @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
    @LoginUser UserDto.Response user
    )  {
        
        if (user != null) {
            model.addAttribute("user", user);
        }

        List<Game> games = gameService.gameList();
        model.addAttribute("games", games);

        List<Gacha> gachas = gachaService.gachaList();
        model.addAttribute("gachas", gachas);

        return "prob/gacha";
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

    @GetMapping("/simulation3")
    public String simulation3(Model model) {
        // Vue 예제 페이지로 이동
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        
        // logger.info("user[{}]", user.toString());

        return "/prob/simulation3";
    } 

    @GetMapping("/simulation4")
    public String simulation4(Model model) {
        // Vue 예제 페이지로 이동
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        
        // logger.info("user[{}]", user.toString());

        return "/prob/simulation4";
    } 

    @GetMapping("/simulation5")
    public String simulation5(Model model) {
        // Vue 예제 페이지로 이동
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        
        // logger.info("user[{}]", user.toString());

        return "/prob/simulation5";
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
