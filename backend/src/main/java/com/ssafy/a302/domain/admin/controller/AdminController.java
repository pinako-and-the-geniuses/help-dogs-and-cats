package com.ssafy.a302.domain.admin.controller;


import com.ssafy.a302.domain.volunteer.service.VolunteerCommentService;
import com.ssafy.a302.domain.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@RestController
@Tag(name = "관리자", description = "관리자 기능 관련 REST API가 작성됩니다.")
public class AdminController {

    private final VolunteerService volunteerService;

    private final VolunteerCommentService volunteerCommentService;






}
