package com.kaist.gachanono.gachanonoserver.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class ChartDataService {
    // int length;
    // final int[] sucCnt;
    // final float[] prob;

    // ChartDataService(int length, int[] sucCnt, float[] prob) {
    //     this.length = length;
    //     this.sucCnt = sucCnt.clone();
    //     this.prob = prob.clone();
    // }

    public HashMap<Integer, Double> barData() {   // 계산된 데이터 중 자연어로 표현할 데이터 선택
        HashMap<Integer, Double> dataMap = new HashMap<Integer, Double>();
        // 작은 횟수 선택해서 넣기
        return dataMap;
    }

    public HashMap<Double, Integer> lineData() {   // 계산된 데이터 중 자연어로 표현할 데이터 선택
        HashMap<Double, Integer> dataMap = new HashMap<Double, Integer>();
        // 작은 횟수 선택해서 넣기
        return dataMap;
    }

}
// item 입력하는 곳에서 버튼 누르면 실행
