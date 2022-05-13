package com.kaist.gachanono.gachanonoserver.service;

import com.kaist.gachanono.gachanonoserver.domain.Game.Game;
import com.kaist.gachanono.gachanonoserver.domain.Game.Item;
import com.kaist.gachanono.gachanonoserver.domain.Game.ProbRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GachaProposeService {

    private final ProbRepository probRepository;

    /**
     * 가챠 추천
     */
    @Transactional 
    public String save(Item item, Game game) {
        return "";
    }

}