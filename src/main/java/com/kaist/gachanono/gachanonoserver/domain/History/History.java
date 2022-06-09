package com.kaist.gachanono.gachanonoserver.domain.History;

import lombok.*;

import javax.persistence.*;

import com.kaist.gachanono.gachanonoserver.domain.User.BaseTimeEntity;
import com.kaist.gachanono.gachanonoserver.domain.User.User;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class History extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String inputprobcsv;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String outputcalcjson;
    
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int good;

    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Long game_id;

    @Column(nullable = false)
    private String gamename;

    @JoinColumn(name = "item_id")
    private Long item_id;

    @Column(nullable = false)
    private String itemname;
    
    @Column(nullable = false)
    private String gachaname;
    
    @JoinColumn(name = "item_id")
    private Long gacha_id;

    @Column(nullable = false)
    private int price;

    // /* 게시글 수정 */
    // public void update(String title, String content) {
    //     this.title = title;
    //     this.content = content;
    // }
}