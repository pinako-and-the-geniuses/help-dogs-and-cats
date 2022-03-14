package com.ssafy.a302.domain.member.controller;

import com.ssafy.a302.domain.member.service.MemberService;
import com.ssafy.a302.global.dto.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test/{email}")
    public BaseResponseDto test(@PathVariable(name = "email") String email) {

        String message = "";
        Object data = null;

        message = "API 호출 성공";
        data = memberService.findMemberByEmail(email).getEmail();

        return BaseResponseDto.builder()
                .message(message)
                .data(data)
                .build();
    }
}
