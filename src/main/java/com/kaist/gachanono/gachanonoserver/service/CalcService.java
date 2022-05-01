package com.kaist.gachanono.gachanonoserver.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalcService {

    @Transactional
    public Map<String, String> samplingWithReplacement(int price, float prob, int tryCnt) {
        return null;
    }

}
