package com.ronbreier.rest;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.annotations.ValidEmail;
import com.ronbreier.annotations.ValidPhoneNumber;
import com.ronbreier.entities.User;
import com.ronbreier.security.CustomUserDetails;
import com.ronbreier.services.EmailService;
import com.ronbreier.services.EmailVerificationService;
import com.ronbreier.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ron Breier on 5/5/2017.
 * Rest controller for User account management
 */

@RestController
@RequestMapping("/rest/account/management")
public class AccountManagmentRestController {

    private static final Logger LOGGER = Logger.getLogger(AccountManagmentRestController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @GetMapping
    public User getUserDetails(@ActiveUser CustomUserDetails userDetails){
        LOGGER.info("Getting user details for userId " + userDetails.getUserId());
        User user = userService.getUpToDateUser(userDetails.getUserId());
        LOGGER.info("User details found " + user);
        return user;
    }

    @PutMapping("/change/email")
    public void saveNewUserEmail(@ActiveUser CustomUserDetails userDetails, @ValidEmail String email){
        User user = userService.getUpToDateUser(userDetails.getUserId());
        LOGGER.info("Changing user id " + user.getUserId() + " email from " + user.getUsername() + " to " + email);
        user.setUsername(email);
        LOGGER.info("Locking user " + user);
        user.setEnabled(0);
        userService.saveUser(user);
        emailVerificationService.generateVerificationUrlExistingUser(user);
        // Send confirmation email to user
        emailService.sendRegistrationEmail(user, false);
        SecurityContextHolder.clearContext();
        LOGGER.info("The User was logged out");
    }

    @PutMapping("/change/name")
    public void saveUserNameChanges(@ActiveUser CustomUserDetails userDetails, String firstName, String lastName){
        User user = userService.getUpToDateUser(userDetails.getUserId());
        LOGGER.info("Changing user id " + user.getUserId() + " name from " + user.getFullName() + " to " + firstName + " " + lastName);
        user.setFirstName(firstName.toUpperCase());
        user.setLastName(lastName.toUpperCase());
        userService.saveUser(user);
    }

    @PutMapping("/change/phonenumber")
    public void saveUserPhoneNumberChanges(@ActiveUser CustomUserDetails userDetails, @ValidPhoneNumber String phoneNumber){
        User user = userService.getUpToDateUser(userDetails.getUserId());
        LOGGER.info("Changing user id " + user.getUsername() + " phone number from " + user.getPhoneNumber() + " to " + phoneNumber);
        user.setPhoneNumber(phoneNumber);
        userService.saveUser(user);
    }

}
