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

    /* completegacha */
    @PostMapping("/calc/completegacha")
    public String completegacha(
        @RequestBody String dto,
        @LoginUser UserDto.Response user
    )throws JSONException {
        log.info("dto[{}]", dto);
        // Parse JSON
        JSONObject d = new JSONObject(dto);
        log.info("d[{}]", d);
        int itemCnt = d.getInt("itemCnt");
        JSONArray itemNamesRaw = d.getJSONArray("itemNames");
        List<String> itemNames = new ArrayList<String>();
        for (int i = 0; i < itemNamesRaw.length(); i++) {
            itemNames.add(itemNamesRaw.getString(i));
        }
        JSONArray itemProbsRaw = d.getJSONArray("itemProbs");
        List<Double> itemProbs = new ArrayList<Double>();
        for (int i = 0; i < itemProbsRaw.length(); i++) {
            Double itemProb = itemProbsRaw.getDouble(i);
            itemProb = itemProb * 0.01;
            itemProbs.add(itemProb);
        }

        log.info(
            "itemCnt[{}] itemList[{}] itemProbs[{}]",
            itemCnt,
            itemNames,
            itemProbs
        );

        // Compute
        return calcService.completeGacha(itemCnt, itemNames, itemProbs);
    }

    /* completegacha */
    @PostMapping("/calc/segDiff")
    public String segDiff(
        @RequestBody String dto,
        @LoginUser UserDto.Response user
    )throws JSONException {
        log.info("dto[{}]", dto);
        // Parse JSON
        JSONObject d = new JSONObject(dto);
        log.info("d[{}]", d);
        int startCnt = d.getInt("startCnt");
        int tryCnt = d.getInt("tryCnt");
        Double itemProb = d.getDouble("itemProb");

        log.info(
            "startCnt[{}] tryCnt[{}] itemProb[{}]",
            startCnt,
            tryCnt,
            itemProb
        );

        // Compute
        return calcService.segDiff(startCnt, tryCnt, itemProb);
    }

    /* completegacha */
    @PostMapping("/calc/segSame")
    public String segSame(
        @RequestBody String dto,
        @LoginUser UserDto.Response user
    )throws JSONException {
        log.info("dto[{}]", dto);
        // Parse JSON
        JSONObject d = new JSONObject(dto);
        log.info("d[{}]", d);
        
        JSONArray startCntsRaw = d.getJSONArray("startCnts");
        List<Integer> startCnts = new ArrayList<Integer>();
        for (int i = 0; i < startCntsRaw.length(); i++) {
            startCnts.add(startCntsRaw.getInt(i));
        }

        JSONArray endCntsRaw = d.getJSONArray("endCnts");
        List<Integer> endCnts = new ArrayList<Integer>();
        for (int i = 0; i < endCntsRaw.length(); i++) {
            endCnts.add(endCntsRaw.getInt(i));
        }

        JSONArray itemProbsRaw = d.getJSONArray("itemProbs");
        List<Double> itemProbs = new ArrayList<Double>();
        for (int i = 0; i < itemProbsRaw.length(); i++) {
            Double itemProb = itemProbsRaw.getDouble(i);
            itemProb = itemProb * 0.01;
            itemProbs.add(itemProb);
        }

        log.info(
            "startCnts[{}] endCnts[{}] itemProbs[{}]",
            startCnts,
            endCnts,
            itemProbs
        );

        // Compute
        return calcService.segSame(startCnts, endCnts, itemProbs);
    }

    /* completegacha */
    @PostMapping("/calc/swrceiling")
    public String swrceiling(
        @RequestBody String dto,
        @LoginUser UserDto.Response user
    )throws JSONException {
        log.info("dto[{}]", dto);
        // Parse JSON
        JSONObject d = new JSONObject(dto);
        log.info("d[{}]", d);
        Double itemProb = d.getDouble("itemProb");
        int tryCnt = d.getInt("tryCnt");
        int maxTryCnt = d.getInt("maxTryCnt");
        log.info(
            "itemProb[{}] tryCnt[{}] maxTryCnt[{}]",
            itemProb,
            tryCnt,
            maxTryCnt
        );

        // Compute
        return calcService.swrCeiling(itemProb, tryCnt, maxTryCnt);
    }

    /* completegacha */
    @PostMapping("/calc/swr")
    public String swr(
        @RequestBody String dto,
        @LoginUser UserDto.Response user
    )throws JSONException {
        log.info("dto[{}]", dto);
        // Parse JSON
        JSONObject d = new JSONObject(dto);
        log.info("d[{}]", d);
        Double itemProb = d.getDouble("itemProb");
        int tryCnt = d.getInt("tryCnt");

        log.info(
            "itemProb[{}] tryCnt[{}]",
            itemProb,
            tryCnt
        );

        // Compute
        return calcService.swr(itemProb, tryCnt);
    }
}
