package com.kaist.gachanono.gachanonoserver.dto;

import lombok.*;

import com.kaist.gachanono.gachanonoserver.domain.History.History;
import com.kaist.gachanono.gachanonoserver.domain.User.User;

/**
 * request, response DTO 클래스를 하나로 묶어 InnerStaticClass로 한 번에 관리
 */
public class HistoryDto {

    /** 확률 계산 히스토리의 등록과 수정을 처리할 요청(Request) 클래스 */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String title;
        private String writer;
        private String inputprobcsv;
        private String outputcalcjson;
        private String createdDate, modifiedDate;
        private int view;
        private User user;
        private String gamename;
        private Long game_id;
        private String itemname;
        private Long item_id;
        private String gachaname;
        private Long gacha_id;
        private int price;

        /* Dto -> Entity */
        public History toEntity() {
            History history = History.builder()
                    .id(id)
                    .title(title)
                    .writer(writer)
                    .inputprobcsv(inputprobcsv)
                    .outputcalcjson(outputcalcjson)
                    .view(0)
                    .user(user)
                    .gamename(gamename)
                    .game_id(game_id)
                    .itemname(itemname)
                    .item_id(item_id)
                    .gachaname(gachaname)
                    .gacha_id(gacha_id)
                    .price(price)
                    .build();

            return history;
        }
    }

    /**
     * 게시글 정보를 리턴할 응답(Response) 클래스
     * Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
     * 별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지
     */
    @Getter
    public static class Response {
        private Long id;
        private String title;
        private String writer;
        private String inputprobcsv;
        private String outputcalcjson;
        private String createdDate, modifiedDate;
        private int view;
        private Long userId;
        private String gamename;
        private Long game_id;
        private String itemname;
        private Long item_id;
        private String gachaname;
        private Long gacha_id;
        private int price;

        /* Entity -> Dto*/
        public Response(History history) {
            this.id = history.getId();
            this.title = history.getTitle();
            this.writer = history.getWriter();
            this.inputprobcsv = history.getInputprobcsv();
            this.outputcalcjson = history.getOutputcalcjson();
            this.createdDate = history.getCreatedDate();
            this.modifiedDate = history.getModifiedDate();
            this.view = history.getView();
            this.userId = history.getUser().getId();
            this.gamename = history.getGamename();
            this.game_id = history.getGame_id();
            this.itemname = history.getItemname();
            this.item_id = history.getItem_id();
            this.gachaname = history.getGachaname();
            this.gacha_id = history.getGacha_id();
            this.price = history.getPrice();
        }
    }
}
