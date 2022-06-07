package com.kaist.gachanono.gachanonoserver.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.kaist.gachanono.gachanonoserver.service.GameService;
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

}
