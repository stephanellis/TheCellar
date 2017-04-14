package com.ronbreier.services;

import com.ronbreier.entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * Created by ron.breier on 4/12/2017.
 */

@Service
public class EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class);

    private JavaMailSender mailSender;

    @Autowired
    private MailTemplateService mailTemplateService;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.doNotReply}")
    private String doNotReply;

    public void sendRegistrationEmail(User user) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(doNotReply);
            messageHelper.setTo(user.getUsername());
            messageHelper.setSubject("Complete Your Registration");
            String content = mailTemplateService.buildRegistrationEmail(user);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
            LOGGER.info("Sent Registration Email");
        } catch (MailException e) {
            LOGGER.error("Something went wrong sending the registration email",e);
        }
    }

}