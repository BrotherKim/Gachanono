package com.kaist.gachanono.gachanonoserver.domain.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaist.gachanono.gachanonoserver.domain.History.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Modifying
    @Query("update History p set p.view = p.view + 1 where p.id = :id")
    int updateView(@Param("id") Long id);

    @Modifying
    @Query("update History p set p.good = p.good + 1 where p.id = :id")
    int updateGood(@Param("id") Long id);

    Page<History> findByTitleContaining(String keyword, Pageable pageable);
}
