package com.ronbreier.services;

import com.ronbreier.entities.EmailVerification;
import com.ronbreier.entities.User;
import com.ronbreier.repositories.EmailVerificationRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by ron.breier on 4/12/2017.
 */

@Service
public class MailTemplateService {

    private static final Logger LOGGER = Logger.getLogger(MailTemplateService.class);

    private TemplateEngine templateEngine;

    @Value("${spring.urls.server}")
    private String baseUrl;

    @Autowired
    EmailVerificationRepository emailVerificationRepository;

    @Autowired
    public MailTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildRegistrationEmail(User user, Boolean isUserNew) {
        LOGGER.info("Building Registration Email");
        // get url for Email Verification
        EmailVerification emailVerification = emailVerificationRepository.findByUserId(user.getUserId());
        Context context = new Context();
        context.setVariable("name", user.getFullName());
        context.setVariable("urlToVerifyEmail", baseUrl + emailVerification.getVerificationUrl());
        context.setVariable("isUserNew", isUserNew);
        return templateEngine.process("emails/completeRegistration", context);
    }

    public String buildEmailResetEmail(User user, String newPass) {
        LOGGER.info("Building Password Reset Email");
        Context context = new Context();
        context.setVariable("name", user.getFullName());
        context.setVariable("newPassword", newPass);
        context.setVariable("urlToLoginScreen", baseUrl + "/login");
        return templateEngine.process("emails/passwordReset", context);
    }

}
