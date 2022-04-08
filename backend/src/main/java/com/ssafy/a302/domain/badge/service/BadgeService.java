package com.ssafy.a302.domain.badge.service;

public interface BadgeService {

    boolean isQualifiedWelcomeBadge(Long memberSeq);

    void approveWelcomeBadge(Long memberSeq);

    boolean isQualifiedCommunicationActivistBadge(Long memberSeq);

    void approveCommunicationActivistBadge(Long memberSeq);

    boolean isQualifiedStartOfHappinessBadge(Long memberSeq);

    void approveStartOfHappinessBadge(Long memberSeq);

    boolean isQualifiedHappinessIsSquareBadge(Long memberSeq);

    void approveHappinessIsSquareBadge(Long memberSeq);

    boolean isQualifiedACarefulObserverBadge(Long memberSeq);

    void approveACarefulObserverBadge(Long memberSeq);

    boolean isQualifiedBraveStepBadge(Long memberSeq);

    void approveBraveStepBadge(Long memberSeq);

    boolean isQualifiedVolunteerRecruitmentKingBadge(Long memberSeq);

    void approveVolunteerRecruitmentKingBadge(Long memberSeq);

    boolean isQualifiedVolunteerRecruitmentKing2Badge(Long memberSeq);

    void approveVolunteerRecruitmentKing2Badge(Long memberSeq);

    boolean isQualifiedVolunteerParticipationKingBadge(Long memberSeq);

    void approveVolunteerParticipationKingBadge(Long memberSeq);

    boolean isQualifiedVolunteerParticipationKing2Badge(Long memberSeq);

    void approveVolunteerParticipationKing2Badge(Long memberSeq);
}
