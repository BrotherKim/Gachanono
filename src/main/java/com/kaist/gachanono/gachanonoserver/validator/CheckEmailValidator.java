package com.kaist.gachanono.gachanonoserver.validator;

import lombok.RequiredArgsConstructor;

import com.kaist.gachanono.gachanonoserver.domain.persistence.UserRepository;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * 이메일 중복 확인 유효성 검증을 위한 커스텀 Validator 클래스
 */
@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<UserDto.Request> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserDto.Request dto, Errors errors) {
        if (userRepository.existsByEmail(dto.toEntity().getEmail())) {
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일 입니다.");
        }
    }
}
