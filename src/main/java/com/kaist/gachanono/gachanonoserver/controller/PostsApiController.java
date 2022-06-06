package com.kaist.gachanono.gachanonoserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.kaist.gachanono.gachanonoserver.config.auth.LoginUser;
import com.kaist.gachanono.gachanonoserver.dto.PostsDto;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;
import com.kaist.gachanono.gachanonoserver.service.GachaService;
import com.kaist.gachanono.gachanonoserver.service.GameService;
import com.kaist.gachanono.gachanonoserver.service.PostsService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller
 */
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;
    private final GameService gameService;
    private final GachaService gachaService;

    /* CREATE */
    @PostMapping("/posts")
    public ResponseEntity save(@RequestBody PostsDto.Request dto, @LoginUser UserDto.Response user) {
        return ResponseEntity.ok(postsService.save(dto, user.getNickname()));
    }

    /* READ */
    @GetMapping("/posts/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return ResponseEntity.ok(postsService.findById(id));
    }

    /* UPDATE */
    @PutMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PostsDto.Request dto) {
        log.info(dto.toString());
        postsService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        postsService.delete(id);
        return ResponseEntity.ok(id);
    }

    /* Get itemlist with game_id */
    @GetMapping("/prob/itemlist/{gameid}")
    public ResponseEntity getItemList(
        @PathVariable Long gameid, 
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable
    ) {
        return ResponseEntity.ok(gameService.getItemList(gameid, pageable));
    }

    /* Get itemlist with game_id */
    @GetMapping("/prob/probtable/{gameid}")
    public ResponseEntity getItemList() {
        return ResponseEntity.ok(gachaService.getGachaInfo());
    }
}