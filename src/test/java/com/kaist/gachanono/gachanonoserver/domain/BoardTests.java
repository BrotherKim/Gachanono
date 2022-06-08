package com.kaist.gachanono.gachanonoserver.domain;

import com.kaist.gachanono.gachanonoserver.config.AppConfig;
import com.kaist.gachanono.gachanonoserver.domain.User.User;
import com.kaist.gachanono.gachanonoserver.domain.Post.Posts;
import com.kaist.gachanono.gachanonoserver.domain.User.Role;



import com.kaist.gachanono.gachanonoserver.domain.persistence.PostsRepository;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


import java.util.List;




@SpringBootTest(classes = { AppConfig.class })
public class BoardTests {



    @Autowired
    PostsRepository postRepository;

    @Test
    void save() {
        Role r = Role.SOCIAL;
        User u = User.builder().id((long)2).username("jaoc1010@gmail.com").nickname("신재욱").email("jaoc1010@gmail.com").password(null).role(r).build();

        // 1. 게시글 파라미터 생성
        Posts params = Posts.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .writer("신재욱")
                .view(0)
                .good(0)
                .user(u)
                .gamename("카트라이더러쉬플러스")
                .game_id((long)1)
                .build();

        // 2. 게시글 저장
        postRepository.save(params);

        // 3. 게시글 정보 조회
        Posts entity = postRepository.findById((long) 10).get();
        assertThat(entity.getTitle()).isEqualTo("게시글 제목");
        assertThat(entity.getContent()).isEqualTo("게시글 내용");
        assertThat(entity.getWriter()).isEqualTo("신재욱");
    }

    @Test
    void findAll() {

        // 1. 전체 게시글 수 조회
        long count_postrepo = postRepository.count();

        // 2. 전체 게시글 리스트 조회
        List<Posts> boards = postRepository.findAll();
        
        assertThat(boards.size() == count_postrepo);

    }



    @Test
    void delete() {

        // 1. 게시글 조회
        Posts entity = postRepository.findById((long) 10).get();
        long brfore_delete = postRepository.count();

        // 2. 게시글 삭제
        postRepository.delete(entity);
        long after_delete = postRepository.count();

        assertThat(brfore_delete == after_delete + 1);


    }

}