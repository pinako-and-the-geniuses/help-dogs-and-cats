package com.ssafy.a302.domain.badge.service;

import com.ssafy.a302.domain.badge.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;
}
