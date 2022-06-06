package com.kaist.gachanono.gachanonoserver.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

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
    public String completeGacha(int itemCnt, List<Long> itemNames, List<Long> itemProbs) {
        return null;
    }

}
