package com.kaist.gachanono.gachanonoserver.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.kaist.gachanono.gachanonoserver.config.auth.LoginUser;
import com.kaist.gachanono.gachanonoserver.dto.HistoryDto;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;
import com.kaist.gachanono.gachanonoserver.service.HistoryService;

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

    private final HistoryService historyService;

    /* CREATE */
    @PostMapping("/save")
    public ResponseEntity save(
        @RequestBody HistoryDto.Request dto,
        @LoginUser UserDto.Response user
    ) {
        return ResponseEntity.ok(historyService.save(dto, user.getNickname()));
    }

    /* READ */
    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return ResponseEntity.ok(historyService.findById(id));
    }

    /* UPDATE */
    // @PutMapping("/{id}")
    // public ResponseEntity update(
    //     @PathVariable Long id,
    //     @RequestBody HistoryDto.Request dto
    // ) {
    //     log.info(dto.toString());
    //     historyService.update(id, dto);
    //     return ResponseEntity.ok(id);
    // }

    /* DELETE */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        historyService.delete(id);
        return ResponseEntity.ok(id);
    }

}
