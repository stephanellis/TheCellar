package com.ronbreier.controllers;

import com.ronbreier.entities.EmailVerification;
import com.ronbreier.entities.User;
import com.ronbreier.repositories.EmailVerificationRepository;
import com.ronbreier.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ron Breier on 4/12/2017.
 */

@Controller
@RequestMapping("/verifyemail")
public class EmailVerificationController {

    private final static Logger LOGGER = Logger.getLogger(EmailVerificationController.class);

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value="/{token}")
    public String verifyEmail(@PathVariable String token){
        LOGGER.info("Email verification API accessed with token " + token);
        EmailVerification emailVerification = emailVerificationRepository.findByToken(token);
        if (emailVerification == null){
            LOGGER.info("Token not found");
            return "pages/registration/registrationError";
        } else{
            LOGGER.info("Found verification record");
            User user = userRepository.findOne(emailVerification.getUserId());
            user.setEnabled(1);
            userRepository.save(user);
            LOGGER.info("User " + user.getUsername() + " enabled");
            emailVerificationRepository.delete(emailVerification.getTokenId());
            LOGGER.info("Verification record deleted: " + emailVerification);
            return "pages/registration/emailVerified";
        }


    }

}
