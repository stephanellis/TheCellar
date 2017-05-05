package com.ronbreier.rest;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.entities.User;
import com.ronbreier.security.CustomUserDetails;
import com.ronbreier.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ron Breier on 5/5/2017.
 * Rest controller for User account management
 */

@RestController
@RequestMapping("/rest/account/management")
public class AccountManagmentRestController {

    private static final Logger LOGGER = Logger.getLogger(AccountManagmentRestController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public User getUserDetails(@ActiveUser CustomUserDetails userDetails){
        LOGGER.info("Getting user details for userId " + userDetails.getUserId());
        User user = userService.getUpToDateUser(userDetails.getUserId());
        LOGGER.info("User details found " + user);
        return user;
    }


}
