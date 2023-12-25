package com.formal.math.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSenderImpl javaMailSender;
    public String createPassword() {
        log.info("-----------createPassword------------");

        char[] chars = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z'
        };

        StringBuffer stringBuffer = new StringBuffer();
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(new Date().getTime());

        int index = 0;
        int length = chars.length;
        for (int i = 0; i < 8; i++) {
            index = secureRandom.nextInt(length);

            if (index % 2 == 0)
                stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
            else
                stringBuffer.append(String.valueOf(chars[index]).toLowerCase());

        }

        log.info("NEW PASSWORD: " + stringBuffer.toString());

        return stringBuffer.toString();

    }
    public void sendPassword(String toMailAddr, String password) {
        System.out.println("---------sendPasswordByMail----------------");
        final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(toMailAddr);
                mimeMessageHelper.setSubject("[Formal Math] A password has been issued.");
                mimeMessageHelper.setText("Password : " + password, true);
            }
        };
        javaMailSender.send(mimeMessagePreparator);
    }
}
