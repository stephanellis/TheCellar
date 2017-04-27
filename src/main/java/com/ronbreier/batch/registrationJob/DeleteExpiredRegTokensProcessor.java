package com.ronbreier.batch.registrationJob;

import com.ronbreier.entities.EmailVerification;
import com.ronbreier.entities.User;
import com.ronbreier.repositories.EmailVerificationRepository;
import com.ronbreier.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ron.breier on 4/27/2017.
 */
public class DeleteExpiredRegTokensProcessor implements ItemProcessor<EmailVerification, EmailVerification> {

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = Logger.getLogger(DeleteExpiredRegTokensProcessor.class);

    @Override
    public EmailVerification process(EmailVerification item) throws Exception {
        EmailVerification emailVerification = emailVerificationRepository.findOne(item.getTokenId());
        if (emailVerification == null){
            return null;
        }else{
            emailVerificationRepository.delete(emailVerification);
            LOGGER.info("Deleted Token " + emailVerification);
            User user = userRepository.findOne(emailVerification.getUserId());
            userRepository.delete(user);
            LOGGER.info("Deleted user " + user);
            return emailVerification;
        }
    }
}
