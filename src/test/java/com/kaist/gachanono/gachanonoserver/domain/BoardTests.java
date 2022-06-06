package com.kaist.gachanono.gachanonoserver.domain;

import com.kaist.gachanono.gachanonoserver.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { AppConfig.class })
public class BoardTests {

    @Test
    void save() {

        // // 1. 게시글 파라미터 생성
        // Board params = Board.builder()
        //         .title("1번 게시글 제목")
        //         .content("1번 게시글 내용")
        //         .writer("도뎡이")
        //         .hits(0)
        //         .deleteYn('N')
        //         .build();

        // // 2. 게시글 저장
        // boardRepository.save(params);

        // // 3. 1번 게시글 정보 조회
        // Board entity = boardRepository.findById((long) 1).get();
        // assertThat(entity.getTitle()).isEqualTo("1번 게시글 제목");
        // assertThat(entity.getContent()).isEqualTo("1번 게시글 내용");
        // assertThat(entity.getWriter()).isEqualTo("도뎡이");
    }

    @Test
    void findAll() {

        // // 1. 전체 게시글 수 조회
        // long boardsCount = boardRepository.count();

        // // 2. 전체 게시글 리스트 조회
        // List<Board> boards = boardRepository.findAll();
    }

    @Test
    void delete() {

        // // 1. 게시글 조회
        // Board entity = boardRepository.findById((long) 1).get();

        // // 2. 게시글 삭제
        // boardRepository.delete(entity);
    }

}