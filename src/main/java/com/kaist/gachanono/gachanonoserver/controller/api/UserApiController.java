package com.kaist.gachanono.gachanonoserver.controller.api;

import lombok.RequiredArgsConstructor;

import com.kaist.gachanono.gachanonoserver.dto.UserDto;
import com.kaist.gachanono.gachanonoserver.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API Controller
 */
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserApiController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @PutMapping("/user")
    public ResponseEntity<String> modify(@RequestBody UserDto.Request dto) {
        userService.modify(dto);

        /* 변경된 세션 등록 */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}