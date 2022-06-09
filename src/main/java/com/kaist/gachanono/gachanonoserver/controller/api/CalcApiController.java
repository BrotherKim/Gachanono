package com.kaist.gachanono.gachanonoserver.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.kaist.gachanono.gachanonoserver.config.auth.LoginUser;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;
import com.kaist.gachanono.gachanonoserver.service.CalcService;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller
 */
@Slf4j
@RequestMapping("/api/calc")
@RequiredArgsConstructor
@RestController
public class CalcApiController {

    private final CalcService calcService;

    /* completegacha */
    @PostMapping("/completegacha")
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
    @PostMapping("/segDiff")
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
        Double itemProb = d.getDouble("itemProb") * 0.01;

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
    @PostMapping("/segSame")
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

        int tryCnt = d.getInt("tryCnt");

        log.info(
            "startCnts[{}] endCnts[{}] itemProbs[{}] tryCnt[{}]",
            startCnts,
            endCnts,
            itemProbs,
            tryCnt
        );

        // Compute
        return calcService.segSame(startCnts, endCnts, itemProbs, tryCnt);
    }

    /* completegacha */
    @PostMapping("/swrCeiling")
    public String swrceiling(
        @RequestBody String dto,
        @LoginUser UserDto.Response user
    )throws JSONException {
        log.info("dto[{}]", dto);
        // Parse JSON
        JSONObject d = new JSONObject(dto);
        log.info("d[{}]", d);
        Double itemProb = d.getDouble("itemProb") * 0.01;
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
    @PostMapping("/swr")
    public String swr(
        @RequestBody String dto,
        @LoginUser UserDto.Response user
    )throws JSONException {
        log.info("dto[{}]", dto);
        // Parse JSON
        JSONObject d = new JSONObject(dto);
        log.info("d[{}]", d);
        Double itemProb = d.getDouble("itemProb") * 0.01;
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
