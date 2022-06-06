package com.kaist.gachanono.gachanonoserver.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kaist.gachanono.gachanonoserver.util.ExecUtil;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalcService {

    /*
     * 공통 사용 함수
     */
    @Transactional
    public float binDist(int n, int k, int p) {
        float pd = 0;
        return pd;
    }

    @Transactional
    public float convertToDecimal(float p) {
        return p;
    }

    /*
     * 복원추출
     */

    @Transactional
    public Map<Integer, Float> getProbTry(float p) {
        return null;
    }

    @Transactional
    public Map<Integer, Integer> minTrial(int price, float prob, int tryCnt) {
        return null;
    }

    @Transactional
    public Map<String, String> samplingWithReplacement(float prob, int try_count) {
        return null;
    }

    /*
     * 복원추출 천장시스템
     */

    @Transactional
    public Map<Integer, Float> getProbTryCeiling(float p, int limit) {
        return null;
    }

    @Transactional
    public Map<Integer, Integer> minTrialCeiling(float p, int limit) {
        return null;
    }

    @Transactional
    public Map<String, String> samplingWithReplacementCeiling(float p, int try_count, int limit) {
        return null;
    }

    /*
     * 구간별 확률(동일 아이템)
     */

    @Transactional
    public float neverPickedSame(int[] range_list, float[] prob_list, int try_pick) {
        return 0;
    }

    @Transactional
    public Map<Integer, Float> getProbTryIntervalSame(int[] range_list, float[] prob_list) {
        return null;
    }

    @Transactional
    public Map<Integer, Integer> minTrialIntervalSame(int[] range_list, float[] prob_list) {
        return null;
    }

    @Transactional
    public Map<String, String> probByIntervalSameItem(int[] range_list, float[] prob_list, int try_pick) {
        return null;
    }

    /*
     * 구간확률(다른 아이템)
     */

    @Transactional
    public float neverPickedDiff(int start_count, float prob, int try_pick) {
        return 0;
    }

    @Transactional
    public Map<Integer, Float> getProbTryIntervalDiff(int start_count, float prob) {
        return null;
    }

    @Transactional
    public Map<Integer, Integer> minTrialIntervalDiff(int start_count, float prob) {
        return null;
    }

    @Transactional
    public Map<String, String> probByIntervalDiffItem(int start_count, float prob, int try_pick) {
        return null;
    }

    /*
     * 컴플리트 가챠
     */

    @Transactional
    public Map<Integer, Float> completeGacha(int all_coupon_count, float[] prob_list) {
        return null;
    }

    @Transactional
    public String completeGacha(int itemCnt, List<String> itemNames, List<Double> itemProbs) {
        String retval = "";
        try {
            String cmd = 
            String.format(
                "python -c \"from src.main.python.compute import *; FUNC_1(%d, %s);\""
                , itemCnt
                , itemProbs.toString()
            );
            log.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            log.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retval;
    }

    @Transactional
    public String segDiff(int itemCnt, List<String> itemNames, List<Double> itemProbs) {
        String retval = "";
        try {
            String cmd = 
            String.format(
                "python -c \"from src.main.python.compute import *; FUNC_2(%d, %s);\""
                , itemCnt
                , itemProbs.toString()
            );
            log.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            log.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retval;
    }

    @Transactional
    public String segSame(int itemCnt, List<String> itemNames, List<Double> itemProbs) {
        String retval = "";
        try {
            String cmd = 
            String.format(
                "python -c \"from src.main.python.compute import *; FUNC_3(%d, %s);\""
                , itemCnt
                , itemProbs.toString()
            );
            log.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            log.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retval;
    }

    @Transactional
    public String swrCeiling(int itemCnt, List<String> itemNames, List<Double> itemProbs) {
        String retval = "";
        try {
            String cmd = 
            String.format(
                "python -c \"from src.main.python.compute import *; FUNC_4(%d, %s);\""
                , itemCnt
                , itemProbs.toString()
            );
            log.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            log.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retval;
    }

    @Transactional
    public String swr(int itemCnt, List<String> itemNames, List<Double> itemProbs) {
        String retval = "";
        try {
            String cmd = 
            String.format(
                "python -c \"from src.main.python.compute import *; FUNC_5(%d, %s);\""
                , itemCnt
                , itemProbs.toString()
            );
            log.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            log.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retval;
    }

}
