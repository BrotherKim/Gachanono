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
    @Query("update History h set h.view = h.view + 1 where h.id = :id")
    int updateView(@Param("id") Long id);

    @Modifying
    @Query("update History h set h.good = h.good + 1 where h.id = :id")
    int updateGood(@Param("id") Long id);
    
    //@Query("SELECT t FROM Tutorial t WHERE t.published=:isPublished AND t.level BETWEEN :start AND :end")
    @Query("select h from History h where h.item_id=:item_id order by good desc, view desc")
    Page<History> recommendGacha(@Param("item_id") Long item_id, Pageable pageable);

    Page<History> findByTitleContaining(String keyword, Pageable pageable);
}
