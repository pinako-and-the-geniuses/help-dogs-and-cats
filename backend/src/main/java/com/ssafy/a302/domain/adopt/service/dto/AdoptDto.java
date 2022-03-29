package com.ssafy.a302.domain.adopt.service.dto;

import lombok.Builder;
import lombok.Getter;

public class AdoptDto {

    @Getter
    public static class AdoptAuth {

        private String title;

        private String content;

        @Builder
        public AdoptAuth(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
