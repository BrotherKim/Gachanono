package com.kaist.gachanono.gachanonoserver.domain.Game;

import java.util.List;

import org.springframework.data.domain.Sort;
import com.kaist.gachanono.gachanonoserver.domain.Board.Board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProbRepository extends JpaRepository<Board, Long> {
    /**
        * 게시글 리스트 조회 - (삭제 여부 기준)
        */
       List<Game> findAllByDeleteYn(final char deleteYn, final Sort sort);
   }