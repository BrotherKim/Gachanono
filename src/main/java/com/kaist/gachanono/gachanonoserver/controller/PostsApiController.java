package com.kaist.gachanono.gachanonoserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.google.gson.Gson;
import com.kaist.gachanono.gachanonoserver.config.auth.LoginUser;
import com.kaist.gachanono.gachanonoserver.dto.PostsDto;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;
import com.kaist.gachanono.gachanonoserver.service.CalcService;
import com.kaist.gachanono.gachanonoserver.service.GachaService;
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
import org.springframework.web.bind.annotation. *;

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
    private final CalcService calcService;

    /* CREATE */
    @PostMapping("/posts")
    public ResponseEntity save(
        @RequestBody PostsDto.Request dto,
        @LoginUser UserDto.Response user
    ) {
        return ResponseEntity.ok(postsService.save(dto, user.getNickname()));
    }

    /* READ */
    @GetMapping("/posts/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return ResponseEntity.ok(postsService.findById(id));
    }

    /* UPDATE */
    @PutMapping("/posts/{id}")
    public ResponseEntity update(
        @PathVariable Long id,
        @RequestBody PostsDto.Request dto
    ) {
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

    /* Get probtable name with game_id */
    @GetMapping("/prob/probtable/{gameid}")
    public ResponseEntity getProbtableName(@PathVariable Long gameid) {
        return ResponseEntity.ok(gameService.getProbtableName(gameid));
    }

    /* CREATE */
    @PostMapping("/calc/completegacha")
    public ResponseEntity completegacha(
        @RequestBody String dto,
        @LoginUser UserDto.Response user
    )throws JSONException {
        // Parse JSON
        JSONObject d = new JSONObject(dto);
        int itemCnt = d.getInt("itemCnt");
        JSONArray itemNamesRaw = d.getJSONArray("itemNames");
        List<Long> itemNames = new ArrayList<Long>();
        for (int i = 0; i < itemNamesRaw.length(); i++) {
            itemNames.add(itemNamesRaw.getLong(i) / 100);
        }
        JSONArray itemProbsRaw = d.getJSONArray("itemProbs");
        List<Long> itemProbs = new ArrayList<Long>();
        for (int i = 0; i < itemProbsRaw.length(); i++) {
            itemProbs.add(itemProbsRaw.getLong(i) / 100);
        }

        log.info(
            "itemCnt[{}] itemList[{}] itemProbs[{}]",
            itemCnt,
            itemNames,
            itemProbs
        );

        // Compute
        return ResponseEntity.ok(calcService.completeGacha(itemCnt, itemNames, itemProbs));
    }
}
