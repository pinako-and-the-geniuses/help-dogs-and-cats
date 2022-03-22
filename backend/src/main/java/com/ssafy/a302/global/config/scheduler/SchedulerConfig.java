package com.ssafy.a302.global.config.scheduler;

import com.ssafy.a302.domain.member.entity.EmailAuth;
import com.ssafy.a302.domain.member.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class SchedulerConfig{

    private final EmailAuthRepository emailAuthRepository;

    @Scheduled(cron = "0 0 12,23 * * *")
    public void deleteExpiredAuthKey(){
        List<EmailAuth> emailAuths = emailAuthRepository.findAll();
        LocalDateTime curDateTime = LocalDateTime.now();
        emailAuths.forEach(a -> {
            if(ChronoUnit.MINUTES.between(a.getCreatedDate(),curDateTime) > 5){
                emailAuthRepository.delete(a);
            }
        });
    }

}
