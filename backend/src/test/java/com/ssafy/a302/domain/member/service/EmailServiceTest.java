package com.ssafy.a302.domain.member.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.ssafy.a302.domain.member.entity.EmailAuth;
import com.ssafy.a302.domain.member.entity.QEmailAuth;
import com.ssafy.a302.domain.member.repository.EmailAuthRepository;
import com.ssafy.a302.domain.member.repository.MemberRepository;
import com.ssafy.a302.domain.member.service.dto.EmailAuthVerifyServiceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.BOOLEAN;

@SpringBootTest
@Transactional
class EmailServiceTest {
    public GreenMail greenMail;

    private String subject;
    private String body;

    private String testEmail;
    private String testAuthKey;


    @Autowired
    private EmailAuthRepository emailAuthRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        emailAuthRepository.deleteAll();

        greenMail = new GreenMail(ServerSetupTest.SMTP_IMAP);

        greenMail.start();

        subject = GreenMailUtil.random();
        body = GreenMailUtil.random();

        testEmail = "test@naver.com";
        testAuthKey = "testtest-3e12-gf3g-3g2g-thisistestaa";


    }

    @Test
    void sendEmailAuthKey() throws MessagingException, IOException {
//        emailAuthRepository.save(new EmailAuth())

        sendTestMails(subject, body);
        assertThat(greenMail.waitForIncomingEmail(5000, 2)).isTrue();

        Message[] messages = greenMail.getReceivedMessages();
        assertThat(messages.length).isEqualTo(2);

        // Simple message
        assertThat(messages[0].getSubject()).isEqualTo(subject);
        assertThat(GreenMailUtil.getBody(messages[0]).trim()).isEqualTo(body);

        //if you send content as a 2 part multipart...
        assertThat(messages[1].getContent() instanceof MimeMultipart).isTrue();
        MimeMultipart mp = (MimeMultipart) messages[1].getContent();
        assertThat(mp.getCount()).isEqualTo(2);
        assertThat(GreenMailUtil.getBody(mp.getBodyPart(0)).trim()).isEqualTo("body1");
        System.out.println(GreenMailUtil.getBody(mp.getBodyPart(0)).trim());
        assertThat(GreenMailUtil.getBody(mp.getBodyPart(1)).trim()).isEqualTo("body2");
        System.out.println(GreenMailUtil.getBody(mp.getBodyPart(1)).trim());

    }


    private void sendTestMails(String subject, String body) throws MessagingException {
        GreenMailUtil.sendTextEmailTest("to@localhost", "from@localhost", subject, body);

        // Create multipart
        MimeMultipart multipart = new MimeMultipart();
        final MimeBodyPart part1 = new MimeBodyPart();
        part1.setContent("body1", "text/plain");
        multipart.addBodyPart(part1);
        final MimeBodyPart part2 = new MimeBodyPart();
        part2.setContent("body2", "text/plain");
        multipart.addBodyPart(part2);

        GreenMailUtil.sendMessageBody("to@localhost", "from@localhost", subject + "__2", multipart, null, ServerSetupTest.SMTP);
    }



}