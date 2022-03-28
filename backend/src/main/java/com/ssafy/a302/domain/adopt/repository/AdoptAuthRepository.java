package com.ssafy.a302.domain.adopt.repository;

import com.ssafy.a302.domain.adopt.entity.AdoptAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdoptAuthRepository extends JpaRepository<AdoptAuth, Long>, AdoptAuthRepositoryCustom {

    @Query("SELECT aa FROM AdoptAuth aa WHERE aa.seq = :seq")
    Optional<AdoptAuth> findBySeq(@Param(value = "seq") Long seq);
}
