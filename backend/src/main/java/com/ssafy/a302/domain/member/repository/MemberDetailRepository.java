package com.ssafy.a302.domain.member.repository;

import com.ssafy.a302.domain.member.entity.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long> {

    boolean existsByNickname(String nickname);
}
