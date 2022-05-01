package com.kaist.gachanono.gachanonoserver.controller;


import java.time.LocalDateTime;
import java.util.List;

import com.kaist.gachanono.gachanonoserver.domain.Board.BoardResponseDto;
import com.kaist.gachanono.gachanonoserver.service.BoardService;
import com.kaist.gachanono.gachanonoserver.service.CalcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calc")
public class CalcPageController {

    @Autowired
    private CalcService calcService;

    /**
     *  복원추출 페이지
     */
    @GetMapping("/samplingWithReplacement")
    public String samplingWithReplacement(int price, float prob, int tryCnt) {
        // 
        calcService.samplingWithReplacement(price, prob, tryCnt);
        return "/";
    }

    /**
     * 게시글 등록 페이지
     */
    @GetMapping("/write")
    public String openBoardWrite(@RequestParam(required = false) final Long id, Model model) {
        model.addAttribute("id", id);
        return "board/write";
    }

    /**
     * 게시글 상세 페이지
     */
    @GetMapping("/view/{id}")
    public String openBoardView(@PathVariable final Long id, Model model) {
        model.addAttribute("id", id);
        return "board/view";
    }

}
