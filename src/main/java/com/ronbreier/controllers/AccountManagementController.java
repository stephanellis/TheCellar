package com.ronbreier.controllers;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.entities.User;
import com.ronbreier.security.CustomUserDetails;
import com.ronbreier.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ron Breier on 5/4/2017.
 * Controller For Account management page
 */

@Controller
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/account/management")
public class AccountManagementController {

    private static final Logger LOGGER = Logger.getLogger(AccountManagementController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public String enterProject(@ActiveUser CustomUserDetails userDetails, Model model) {
        User user = userService.getUpToDateUser(userDetails.getUserId());
        LOGGER.info("Navigating to Account Management Screen for user " +  user);
        model.addAttribute("user", user);
        return "pages/user/management";
    }


}
