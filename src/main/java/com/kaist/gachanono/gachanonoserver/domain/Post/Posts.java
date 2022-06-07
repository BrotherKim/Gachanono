package com.kaist.gachanono.gachanonoserver.domain.Post;

import lombok.*;

import javax.persistence.*;

import com.kaist.gachanono.gachanonoserver.domain.User.BaseTimeEntity;
import com.kaist.gachanono.gachanonoserver.domain.User.User;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int good;

    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Long game_id;

    @Column(nullable = false)
    private String gamename;
    

    @OneToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;

    /* 게시글 수정 */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}