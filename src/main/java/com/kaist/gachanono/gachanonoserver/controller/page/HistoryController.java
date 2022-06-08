package com.kaist.gachanono.gachanonoserver.controller.page;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import com.kaist.gachanono.gachanonoserver.config.auth.LoginUser;
import com.kaist.gachanono.gachanonoserver.domain.Game.Gacha;
import com.kaist.gachanono.gachanonoserver.domain.Game.Game;
import com.kaist.gachanono.gachanonoserver.domain.History.History;
import com.kaist.gachanono.gachanonoserver.dto.HistoryDto;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;
import com.kaist.gachanono.gachanonoserver.service.GachaService;
import com.kaist.gachanono.gachanonoserver.service.GameService;
import com.kaist.gachanono.gachanonoserver.service.HistoryService;

/**
 * 화면 연결 Controller
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class HistoryController {

    private final HistoryService historyService;
    private final GameService gameService;
    private final GachaService gachaService;

    /* default page = 0, size = 10  */
    @GetMapping("/history/list")
    public String free(
        Model model,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
        @LoginUser UserDto.Response user
    ) {
        Page<History> list = historyService.pageList(pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }

        model.addAttribute("histories", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        return "history/list";
    }

    /* 히스토리 작성 */
    @GetMapping("/history/write")
    public String write(@LoginUser UserDto.Response user, Model model) {
        List<Game> games = gameService.gameList();
        model.addAttribute("games", games);

        if (user != null) {
            model.addAttribute("user", user);
        }
        return "history/history-write";
    }

    /* 히스토리 상세보기 */
    @GetMapping("/history/read/{id}")
    public String read(
        @PathVariable Long id,
        @LoginUser UserDto.Response user,
        Model model
    ) {
        HistoryDto.Response dto = historyService.findById(id);

        
        /* 사용자 관련 */
        if (user != null) {
            model.addAttribute("user", user);

            /* 게시히스토리 작성자 본인인지 확인 */
            if (dto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }

        }

        historyService.updateView(id); // views ++
        model.addAttribute("history", dto);
        return "history/history-read";
    }

    @GetMapping("/history/update/{id}")
    public String update(
        @PathVariable Long id,
        @LoginUser UserDto.Response user,
        Model model
    ) {
        List<Game> games = gameService.gameList();
        model.addAttribute("games", games);

        HistoryDto.Response dto = historyService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("history", dto);

        return "history/history-update";
    }

    @GetMapping("/history/good/{id}")
    public String good(
        @PathVariable Long id,
        @LoginUser UserDto.Response user,
        Model model
    ) {
        HistoryDto.Response dto = historyService.findById(id);

        /* 사용자 관련 */
        if (user != null) {
            model.addAttribute("user", user);

            /* 게시히스토리 작성자 본인인지 확인 */
            if (dto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }
        }

        historyService.updateGood(id); // goods ++
        model.addAttribute("history", dto);
        return "history/history-read";
    }

    @GetMapping("/history/search")
    public String search(
        String keyword,
        Model model,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
        @LoginUser UserDto.Response user
    ) {
        Page<History> searchList = historyService.search(keyword, pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", searchList.hasNext());
        model.addAttribute("hasPrev", searchList.hasPrevious());

        return "history/history-search";
    }
    
    /* Calc */
    @GetMapping("/history/calc/{id}")
    public String calc(
        @PathVariable Long id,
        @LoginUser UserDto.Response user,
        Model model
    ) {
        HistoryDto.Response dto = historyService.findById(id);
        
        /* 사용자 관련 */
        if (user != null) {
            model.addAttribute("user", user);

            /* 게시히스토리 작성자 본인인지 확인 */
            if (dto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }
        }

        model.addAttribute("history", dto);

        
        List<Gacha> gachas = gachaService.gachaList();
        model.addAttribute("gachas", gachas);

        return "history/calc";
    }
}
