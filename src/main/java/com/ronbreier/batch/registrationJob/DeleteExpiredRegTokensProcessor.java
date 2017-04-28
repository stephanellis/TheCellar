package com.ronbreier.batch.registrationJob;

import com.ronbreier.entities.EmailVerification;
import com.ronbreier.entities.User;
import com.ronbreier.entities.UserRole;
import com.ronbreier.repositories.EmailVerificationRepository;
import com.ronbreier.repositories.UserRepository;
import com.ronbreier.repositories.UserRolesRepository;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ron.breier on 4/27/2017.
 */
public class DeleteExpiredRegTokensProcessor implements ItemProcessor<EmailVerification, EmailVerification> {

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

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
            List<UserRole> userRoles = userRolesRepository.findByUserId(user.getUserId());
            userRolesRepository.delete(userRoles);
            LOGGER.info("Deleted User Roles " + userRoles);
            userRepository.delete(user);
            LOGGER.info("Deleted user " + user);
            return emailVerification;
        }
    }
}
