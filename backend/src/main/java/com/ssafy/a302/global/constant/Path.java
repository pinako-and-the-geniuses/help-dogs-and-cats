package com.ssafy.a302.global.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class Path {

    public static String PROFILE_IMAGE_ACCESS_PATH;

    public static String BADGE_IMAGE_ACCESS_PATH;

    @Autowired
    public Path(MessageSource messageSource) {
        PROFILE_IMAGE_ACCESS_PATH = messageSource.getMessage("path.access.files.images.profile", null, null);
        BADGE_IMAGE_ACCESS_PATH = messageSource.getMessage("path.access.files.images.badge", null, null);
    }
}
