package com.ssafy.a302.domain.memberbadge.entity;

import java.io.Serializable;
import java.util.Objects;

public class MemberBadgeSeq implements Serializable {
    private Long member;

    private Long badge;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberBadgeSeq that = (MemberBadgeSeq) o;
        return Objects.equals(member, that.member) && Objects.equals(badge, that.badge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, badge);
    }
}
