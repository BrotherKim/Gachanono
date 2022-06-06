package com.kaist.gachanono.gachanonoserver.domain.Game;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GachaRepository extends JpaRepository<Item, Long> {
    // @Modifying
    // @Query("update Posts p set p.view = p.view + 1 where p.id = :id")
    // int updateView(@Param("id") Long id);

    // @Modifying
    // @Query("update Posts p set p.good = p.good + 1 where p.id = :id")
    // int updateGood(@Param("id") Long id);

    // Page<Posts> findByContentContaining(String keyword, Pageable pageable);
    List<Item> findAll();
}
