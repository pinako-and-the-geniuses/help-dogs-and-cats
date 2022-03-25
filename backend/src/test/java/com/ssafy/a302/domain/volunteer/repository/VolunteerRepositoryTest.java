package com.ssafy.a302.domain.volunteer.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VolunteerRepositoryTest {

    @Test
    @DisplayName("기본키로 회원 엔티티 조회")
    void findBySeq() {
    }
}