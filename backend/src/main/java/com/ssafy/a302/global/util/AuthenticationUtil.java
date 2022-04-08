package com.ssafy.a302.global.util;

import com.ssafy.a302.global.auth.CustomUserDetails;
import com.ssafy.a302.global.constant.ErrorMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {

    public void verifyMemberSeq(Authentication authentication, Long memberSeq) {
        if (((CustomUserDetails) authentication.getDetails()).getMember().getSeq().equals(memberSeq)) {
            return;
        }

        throw new AccessDeniedException(ErrorMessage.INVALID_MEMBER_SEQ);
    }

    public Long getMemberSeq(Authentication authentication) {
        return ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
    }
}
