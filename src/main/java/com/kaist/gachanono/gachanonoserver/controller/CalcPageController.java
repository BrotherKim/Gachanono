package com.kaist.gachanono.gachanonoserver.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.kaist.gachanono.gachanonoserver.domain.Board.BoardResponseDto;
import com.kaist.gachanono.gachanonoserver.service.CalcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calc")
public class CalcPageController {

    @Autowired
    private CalcService calcService;

    /**
     * 복원추출 페이지
     */
    @GetMapping("/samplingWithReplacement")
    public String samplingWithReplacement(float prob, int try_count) {
        //
        calcService.samplingWithReplacement(prob, try_count);
        return "/";
    }

    /**
     * 복원추출 천장시스템
     */
    @GetMapping("/samplingWithReplacementCeiling")
    public String samplingWithReplacementCeiling(float p, int try_count, int limit) {
        //
        calcService.samplingWithReplacementCeiling(p, try_count, limit);
        return "/";
    }

    /**
     * 구간별 확률(동일 아이템)
     */
    @GetMapping("/probByIntervalSameItem")
    public String probByIntervalSameItem(int[] range_list, float[] prob_list, int try_pick) {
        //
        calcService.probByIntervalSameItem(range_list, prob_list, try_pick);
        return "/";
    }

    /**
     * 구간확률(다른 아이템)
     */
    @GetMapping("/probByIntervalDiffItem")
    public String probByIntervalDiffItem(int start_count, float prob, int try_pick) {
        //
        calcService.probByIntervalDiffItem(start_count, prob, try_pick);
        return "/";
    }

    /**
     * 컴플리트 가챠
     */
    @GetMapping("/completeGacha")
    public String completeGacha(int all_coupon_count, float[] prob_list) {
        //
        calcService.completeGacha(all_coupon_count, prob_list);
        return "/";
    }

}
