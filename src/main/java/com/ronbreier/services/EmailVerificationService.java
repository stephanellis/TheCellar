package com.ronbreier.services;

import com.ronbreier.entities.EmailVerification;
import com.ronbreier.entities.User;
import com.ronbreier.repositories.EmailVerificationRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ron Breier on 4/12/2017.
 */

@Service
public class EmailVerificationService {

    private static final Logger LOGGER = Logger.getLogger(EmailVerificationService.class);

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    public void generateVerificationUrl(User user){
        LOGGER.info("Generating an email verification key for new user " + user.getUsername());
        StringBuilder token = new StringBuilder(100);
        EmailVerification emailVerification = new EmailVerification(user);
        emailVerificationRepository.save(emailVerification);
        LOGGER.info("New token object: " + emailVerification);
    }


}
