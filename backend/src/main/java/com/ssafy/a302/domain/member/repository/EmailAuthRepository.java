package com.ssafy.a302.domain.member.repository;

import com.ssafy.a302.domain.member.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, String> {

    Optional<EmailAuth> findEmailAuthByEmailAndAuthKey(String email, String authKey);

    boolean existsEmailAuthByEmailAndAuthKey(String email, String authKey);

}
