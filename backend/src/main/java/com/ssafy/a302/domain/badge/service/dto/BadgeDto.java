package com.ssafy.a302.domain.badge.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString(of = {"seq", "name", "content", "imageFilename", "howToGet"})
public class BadgeDto {

    private final Long seq;

    private final String name;

    private final String content;

    private final String howToGet;

    private final String imageFilename;

    @QueryProjection
    public BadgeDto(Long seq, String name, String content, String howToGet, String imageFilename) {
        this.seq = seq;
        this.name = name;
        this.content = content;
        this.howToGet = howToGet;
        this.imageFilename = imageFilename;
    }

    @SuperBuilder
    @Getter
    @ToString(of = {"imageFilePath", "achieve"})
    public static class ForProfile extends BadgeDto {

        @JsonIgnore
        private String imageFilename;

        private final String imageFilePath;

        private boolean achieve;

        @QueryProjection
        public ForProfile(Long seq, String name, String content, String howToGet, String imageFilename) {
            super(seq, name, content, howToGet, imageFilename);
            this.imageFilePath = imageFilename == null ? null : "static/images/badge/" + imageFilename;
        }

        public void achieve() {
            this.achieve = true;
        }
    }
}
