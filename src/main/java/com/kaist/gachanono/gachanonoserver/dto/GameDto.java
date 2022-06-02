package com.kaist.gachanono.gachanonoserver.dto;

import lombok.*;

import com.kaist.gachanono.gachanonoserver.domain.Game.Game;

/**
 * request, response DTO 클래스를 하나로 묶어 InnerStaticClass로 한 번에 관리
 */
public class GameDto{

    /** 게임 request를 위한 DTO 클래스 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String gamename;
        /* Dto -> Entity */
        public Game toEntity() {
            Game game = Game.builder()
                    .id(id)
                    .gamename(gamename)
                    .build();

            return game;
        }
    }

    /**
     * 게임 정보를 리턴할 응답(Response) 클래스
     * Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
     * 별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지
     */
    @RequiredArgsConstructor
    @Getter
    public static class Response {
        private Long id;
        private String gamename;
        /* Dto -> Entity */
        public Game toEntity() {
            Game game = Game.builder()
                    .id(id)
                    .gamename(gamename)
                    .build();

            return game;
        }
    }
}
