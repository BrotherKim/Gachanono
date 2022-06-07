package com.kaist.gachanono.gachanonoserver.domain.Game;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAll();
}
