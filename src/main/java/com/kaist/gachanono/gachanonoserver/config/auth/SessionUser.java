package com.kaist.gachanono.gachanonoserver.config.auth;

import lombok.Data;

import java.io.Serializable;

import com.kaist.gachanono.gachanonoserver.domain.User.User;

/*
 * 세션에 저장하려면 직렬화를 해야 하는데
 * User 엔티티는 추후 변경사항이 있을 수 있기 때문에
 * 직렬화를 하기 위한 별도의 SessionUser 클래스 생성
 */
@Data
public class SessionUser implements Serializable {
    private String name, email, role;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}