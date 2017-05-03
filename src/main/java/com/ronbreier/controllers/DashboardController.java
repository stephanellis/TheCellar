package com.ronbreier.controllers;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.security.CustomUserDetails;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by Ron Breier on 4/6/2017.
 */

@Controller
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/dashboard")
public class DashboardController {

    private static final Logger LOGGER = Logger.getLogger(DashboardController.class);

    @GetMapping
    public String enterProject(@ActiveUser CustomUserDetails userDetails, Model model, HttpSession session){
        LOGGER.info("Dashboard Accessed");
        session.setAttribute("resetPassword", userDetails.getUser().isPasswordReset());
        if(userDetails.getUser().isPasswordReset()){
            LOGGER.info("This user needs to reset their password");
        }
        return "pages/user/dashboard";
    }
}
