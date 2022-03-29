package com.ssafy.a302.domain.admin.controller;


import com.ssafy.a302.domain.admin.service.AdminService;
import com.ssafy.a302.domain.admin.service.dto.VolunteerAuthDto;
import com.ssafy.a302.domain.volunteer.controller.dto.VolunteerRequestDto;
import com.ssafy.a302.domain.volunteer.service.VolunteerCommentService;
import com.ssafy.a302.domain.volunteer.service.VolunteerService;
import com.ssafy.a302.global.constant.Message;
import com.ssafy.a302.global.dto.BaseResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
@RestController
@Tag(name = "관리자", description = "관리자 기능 관련 REST API가 작성됩니다.")
public class AdminController {

    private final VolunteerService volunteerService;

    private final VolunteerCommentService volunteerCommentService;

    private final AdminService adminService;


    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/volunteerAuth/{volunteerSeq}")
    public BaseResponseDto<?> volunteerAuthDetail(@PathVariable Long volunteerSeq,
                                                   Authentication authentication) {
        VolunteerAuthDto.Response findVolunteerAuth = adminService.volunteerAuthDetail(volunteerSeq);

        return BaseResponseDto.builder()
                .message(Message.SUCCESS_ADMIN_VOLUNTEER_AUTH)
                .data(findVolunteerAuth)
                .build();
    }




}