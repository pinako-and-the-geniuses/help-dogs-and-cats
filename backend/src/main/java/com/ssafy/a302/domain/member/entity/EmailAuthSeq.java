package com.ssafy.a302.domain.member.entity;

import java.io.Serializable;
import java.util.Objects;

public class EmailAuthSeq implements Serializable {
    private String email;

    private String authKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAuthSeq that = (EmailAuthSeq) o;
        return Objects.equals(email, that.email) && Objects.equals(authKey, that.authKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, authKey);
    }
}
