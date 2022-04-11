package com.kaist.gachanono.gachanonoserver.controller;


import java.time.LocalDateTime;
import java.util.List;

import com.kaist.gachanono.gachanonoserver.domain.Board.BoardResponseDto;
import com.kaist.gachanono.gachanonoserver.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
public class BoardPageController {

    @Autowired
    private BoardService boardService;

    /**
     * 게시글 리스트 페이지
     */
    @GetMapping("/list")
    public String openBoardList(Model model) {
        List<BoardResponseDto> boards = boardService.findAllByDeleteYn('N');
        model.addAttribute("boards", boards);
        model.addAttribute("nowDate", LocalDateTime.now());
        return "board/list";
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
