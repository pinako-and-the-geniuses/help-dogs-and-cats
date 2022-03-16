package com.ssafy.a302.domain.volunteer.entity;

import java.io.Serializable;
import java.util.Objects;

public class VolunteerMemberSeq implements Serializable {

    private Long volunteer;

    private Long member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolunteerMemberSeq that = (VolunteerMemberSeq) o;
        return Objects.equals(volunteer, that.volunteer) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volunteer, member);
    }
}
