package com.kaist.gachanono.gachanonoserver.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaist.gachanono.gachanonoserver.domain.History.History;
import com.kaist.gachanono.gachanonoserver.service.GameService;
import com.kaist.gachanono.gachanonoserver.service.HistoryService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller
 */
@Slf4j
@RequestMapping("/api/prob")
@RequiredArgsConstructor
@RestController
public class ProbApiController {

    private final GameService gameService;
    private final HistoryService historyService;

    /* Get itemlist with game_id */
    @GetMapping("/itemlist/{gameid}")
    public ResponseEntity getItemList(
        @PathVariable Long gameid,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable
    ) {
        return ResponseEntity.ok(gameService.getItemList(gameid, pageable));
    }

    /* Get probtable name with game_id */
    @GetMapping("/probtable/{gameid}")
    public ResponseEntity getProbtableName(@PathVariable Long gameid) {
        return ResponseEntity.ok(gameService.getProbtableName(gameid));
    }

    
    /* Get recommend gachas from history */
    @JsonIgnore
    @GetMapping("/recommend/{itemid}")
    public ResponseEntity recommendGacha(@PathVariable Long itemid) {
        Page<History> list = historyService.recommendGacha(itemid, PageRequest.of(0, 5));
        return ResponseEntity.ok(list);
    }

    

}
