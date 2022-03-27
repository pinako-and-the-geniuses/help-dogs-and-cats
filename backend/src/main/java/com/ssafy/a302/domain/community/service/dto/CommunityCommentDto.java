package com.ssafy.a302.domain.community.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
public class CommunityCommentDto {

    @Getter
    @ToString(of = {"content", "parentSeq"})
    public static class RegisterInfo {

        private final String content;

        private final Long parentSeq;

        @Builder
        public RegisterInfo(String content, Long parentSeq) {
            this.content = content;
            this.parentSeq = parentSeq;
        }
    }
}
