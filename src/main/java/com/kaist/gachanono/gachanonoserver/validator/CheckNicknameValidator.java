package com.kaist.gachanono.gachanonoserver.validator;

import lombok.RequiredArgsConstructor;

import com.kaist.gachanono.gachanonoserver.domain.persistence.UserRepository;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * 닉네임 중복 확인 유효성 검증을 위한 커스텀 Validator 클래스
 */
@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<UserDto.Request> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserDto.Request dto, Errors errors) {
        if (userRepository.existsByNickname(dto.toEntity().getNickname())) {
            errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용중인 닉네임 입니다.");
        }
    }
}
