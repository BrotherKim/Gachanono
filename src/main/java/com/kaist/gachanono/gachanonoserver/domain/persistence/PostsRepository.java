package com.kaist.gachanono.gachanonoserver.domain.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaist.gachanono.gachanonoserver.domain.Post.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Modifying
    @Query("update Posts p set p.view = p.view + 1 where p.id = :id")
    int updateView(@Param("id") Long id);

    @Modifying
    @Query("update Posts p set p.good = p.good + 1 where p.id = :id")
    int updateGood(@Param("id") Long id);

    Page<Posts> findByTitleContaining(String keyword, Pageable pageable);
    Page<Posts> findByContentContaining(String keyword, Pageable pageable);
    Page<Posts> findByTitleContainingOrContentContainingOrWriterContainingOrGamenameContaining(String k1, String k2, String k3, String k4, Pageable pageable);
}
