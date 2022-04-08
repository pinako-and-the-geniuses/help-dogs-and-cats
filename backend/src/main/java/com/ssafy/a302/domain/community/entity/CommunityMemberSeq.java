package com.ssafy.a302.domain.community.entity;

import java.io.Serializable;
import java.util.Objects;

public class CommunityMemberSeq implements Serializable {

    private Long community;

    private Long member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommunityMemberSeq that = (CommunityMemberSeq) o;
        return Objects.equals(community, that.community) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(community, member);
    }
}
