package com.kaist.gachanono.gachanonoserver.dao;

import com.kaist.gachanono.gachanonoserver.domain.User.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //로그인 & 회원가입
    User findByEmail(String email);
    void save(User user);
}