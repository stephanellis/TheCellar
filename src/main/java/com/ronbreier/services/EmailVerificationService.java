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

    public void generateVerificationUrlUser(User user, boolean isUserNew){
        LOGGER.info("Generating an email verification key for user " + user.getUsername());
        EmailVerification emailVerification = new EmailVerification(user);
        emailVerification.setNewUser(isUserNew);
        emailVerificationRepository.save(emailVerification);
        LOGGER.info("New token object: " + emailVerification);
    }

}
