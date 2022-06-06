package com.kaist.gachanono.gachanonoserver.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.kaist.gachanono.gachanonoserver.domain.Game.Gacha;
import com.kaist.gachanono.gachanonoserver.domain.Game.GachaRepository;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GachaService {

    private final GachaRepository gachaRepository;

    @Transactional(readOnly = true)
    public List<Gacha> gachaList() {
        return gachaRepository.findAll();
    }
    
}
