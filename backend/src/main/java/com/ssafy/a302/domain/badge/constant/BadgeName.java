package com.ssafy.a302.domain.badge.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class BadgeName {

    public static String WELCOME_BADGE;

    public static String COMMUNICATION_ACTIVIST_BADGE;

    public static String START_OF_HAPPINESS_BADGE;

    public static String HAPPINESS_IS_SQUARE_BADGE;

    public static String A_CAREFUL_OBSERVER_BADGE;

    public static String BRAVE_STEP_BADGE;

    public static String VOLUNTEER_RECRUITMENT_KING_BADGE;

    public static String VOLUNTEER_RECRUITMENT_KING2_BADGE;

    public static String VOLUNTEER_PARTICIPATION_KING_BADGE;

    public static String VOLUNTEER_PARTICIPATION_KING2_BADGE;

    @Autowired
    public BadgeName(MessageSource messageSource) {
        WELCOME_BADGE = messageSource.getMessage("welcome-badge", null, null);
        COMMUNICATION_ACTIVIST_BADGE = messageSource.getMessage("communication-activist-badge", null, null);
        START_OF_HAPPINESS_BADGE = messageSource.getMessage("start-of-happiness-badge", null, null);
        HAPPINESS_IS_SQUARE_BADGE= messageSource.getMessage("happiness-is-square-badge", null, null);
        A_CAREFUL_OBSERVER_BADGE= messageSource.getMessage("a-careful-observer-badge", null, null);
        BRAVE_STEP_BADGE = messageSource.getMessage("brave-step-badge", null, null);
        VOLUNTEER_RECRUITMENT_KING_BADGE = messageSource.getMessage("volunteer-recruitment-king-badge", null, null);
        VOLUNTEER_RECRUITMENT_KING2_BADGE = messageSource.getMessage("volunteer-recruitment-king2-badge", null, null);
        VOLUNTEER_PARTICIPATION_KING_BADGE = messageSource.getMessage("volunteer-participation-king-badge", null, null);
        VOLUNTEER_PARTICIPATION_KING2_BADGE = messageSource.getMessage("volunteer-participation-king2-badge", null, null);
    }
}
