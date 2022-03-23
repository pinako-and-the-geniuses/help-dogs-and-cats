package com.ssafy.a302.domain.member.controller;

import com.ssafy.a302.domain.member.service.MemberService;
import com.ssafy.a302.global.util.AuthenticationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
@Tag(name = "회원 프로필", description = "회원 프로필 관련 REST API가 작성됩니다.")
public class ProfileController {

    private final MemberService memberService;

    private final AuthenticationUtil authenticationUtil;
}
