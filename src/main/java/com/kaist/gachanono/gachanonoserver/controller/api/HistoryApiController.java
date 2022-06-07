package com.kaist.gachanono.gachanonoserver.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.kaist.gachanono.gachanonoserver.config.auth.LoginUser;
import com.kaist.gachanono.gachanonoserver.dto.PostsDto;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;
import com.kaist.gachanono.gachanonoserver.service.CalcService;
import com.kaist.gachanono.gachanonoserver.service.GameService;
import com.kaist.gachanono.gachanonoserver.service.PostsService;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller
 */
@Slf4j
@RequestMapping("/api/history")
@RequiredArgsConstructor
@RestController
public class HistoryApiController {

    private final PostsService postsService;

    /* CREATE */
    @PostMapping("/save")
    public ResponseEntity save(
        @RequestBody PostsDto.Request dto,
        @LoginUser UserDto.Response user
    ) {
        return ResponseEntity.ok(postsService.save(dto, user.getNickname()));
    }

    /* READ */
    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return ResponseEntity.ok(postsService.findById(id));
    }

    /* UPDATE */
    @PutMapping("/{id}")
    public ResponseEntity update(
        @PathVariable Long id,
        @RequestBody PostsDto.Request dto
    ) {
        log.info(dto.toString());
        postsService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        postsService.delete(id);
        return ResponseEntity.ok(id);
    }

}
