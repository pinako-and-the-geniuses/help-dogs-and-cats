package com.ssafy.a302.domain.adopt.service;

import com.ssafy.a302.domain.adopt.repository.AdoptAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdoptServiceImpl implements AdoptAuthService {

    private final AdoptAuthRepository adoptAuthRepository;
}
