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
@RequestMapping("/chart")
public class ChartPageController {

    /**
     * chpater3
     */
    @GetMapping("/chapter3")
    public String chapter3(Model model) {
        return "chart/chapter3";
    }

}
