package com.kaist.gachanono.gachanonoserver.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kaist.gachanono.gachanonoserver.util.ExecUtil;
import com.kaist.gachanono.gachanonoserver.util.OSValidator;

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
            String pyCmd = OSValidator.isWindows() ? "python" : "python3";
            String cmd = 
            String.format(
                "%s -c \"from src.main.python.compute import *; FUNC_1(%d, %s);\""
                , pyCmd
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

        retval = retval.replace("(", "").replace(")", "").replace("},", "}},");
        return retval;
    }

    @Transactional
    public String segDiff(int startCnt, int tryCnt, Double itemProb) {
        String retval = "";
        try {
            String pyCmd = OSValidator.isWindows() ? "python" : "python3";
            String cmd = 
            String.format(
                "%s -c \"from src.main.python.compute import *; FUNC_2(%d, %f, %d);\""
                , pyCmd
                , startCnt
                , itemProb
                , tryCnt
            );
            log.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            log.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        retval = retval.replace("(", "").replace(")", "").replace("},", "}},");
        return retval;
    }

    @Transactional
    public String segSame(List<Integer> startCnts, List<Integer> endCnts, List<Double> itemProbs, int tryCnt) {
        String retval = "";

        String ranges = "[";
        int rangeCnt = startCnts.size();
        for(int i = 0; i < rangeCnt; i++) {
            ranges += String.format("[%d, %d]", startCnts.get(i), endCnts.get(i));
            if(i < rangeCnt - 1) {
                ranges += ", ";
            }
        }
        ranges += "]";
        // startCnts = [1, 11, 21]
        // endsCnts = [10, 20, 30]

        // ranges = [[1, 10], [11, 20], [21, 30]]

        log.info("ranges: {}", ranges);

        try {
            String pyCmd = OSValidator.isWindows() ? "python" : "python3";
            String cmd = 
            String.format(
                "%s -c \"from src.main.python.compute import *; FUNC_3(%s, %s, %d);\""
                , pyCmd
                , ranges
                , itemProbs.toString()
                , tryCnt
            );
            log.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            log.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        retval = retval.replace("(", "").replace(")", "").replace("},", "}},");
        return retval;
    }

    @Transactional
    public String swrCeiling(Double itemProb, int tryCnt, int maxTryCnt) {
        String retval = "";
        try {
            String pyCmd = OSValidator.isWindows() ? "python" : "python3";
            String cmd = 
            String.format(
                "%s -c \"from src.main.python.compute import *; FUNC_4(%f, %d, %d);\""
                , pyCmd
                , itemProb
                , tryCnt
                , maxTryCnt
            );
            log.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            log.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        retval = retval.replace("(", "").replace(")", "").replace("},", "}},");
        return retval;
    }

    @Transactional
    public String swr(Double itemProb, int tryCnt) {
        String retval = "";
        try {
            String pyCmd = OSValidator.isWindows() ? "python" : "python3";
            String cmd = 
            String.format(
                "%s -c \"from src.main.python.compute import *; FUNC_5(%f, %d);\""
                , pyCmd
                , itemProb
                , tryCnt
            );
            log.info(cmd);
            ExecUtil.getInstance().exec(cmd);
            retval = ExecUtil.getInstance().getStdOutString();
            log.info("retval: {}", retval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        retval = retval.replace("(", "").replace(")", "").replace("},", "}},");
        return retval;
    }

    
    
}
