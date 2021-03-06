package com.ronbreier.controllers;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.entities.User;
import com.ronbreier.forms.ChoosePasswordForm;
import com.ronbreier.forms.ResetPasswordForm;
import com.ronbreier.repositories.UserRepository;
import com.ronbreier.security.CustomUserDetails;
import com.ronbreier.services.CustomUserDetailsService;
import com.ronbreier.services.EmailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by Ron Breier on 4/28/2017.
 * Cotroller for the Password Reset Functionality
 */

@Controller
@RequestMapping("/passwordreset")
public class PasswordResetController {

    private static final Logger LOGGER = Logger.getLogger(PasswordResetController.class);

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String getPasswordResetForm(Model model){
        LOGGER.info("Navigating to the Password Reset Form");
        model.addAttribute("resetPasswordForm", new ResetPasswordForm());
        return "pages/registration/passwordReset";
    }

    @PostMapping
    public String submitPasswordResetForm(@ModelAttribute("resetPasswordForm") @Valid ResetPasswordForm resetPasswordForm,
                                     BindingResult result, Errors errors, Model model){
        if(result.hasErrors()){
            LOGGER.info("There was an error on the reset form");
            errors.getAllErrors().stream().forEach(e -> LOGGER.info(e.toString()));
            return "pages/registration/passwordReset";
        }else {
            LOGGER.info("Password reset form submitted successfully");
            User userToReset = userRepository.findByUsername(resetPasswordForm.getEmail());
            if(userToReset != null){
                userDetailsService.resetPassword(userToReset);
            }
            return "pages/registration/passwordResetSuccess";
        }
    }

    @GetMapping("/setnewpassword")
    public String chooseNewPasswordForm(Model model){
        LOGGER.info("Navigating to the Choose Password Form");
        model.addAttribute("choosePasswordForm", new ChoosePasswordForm());
        return "pages/registration/choosePassword";
    }

    @PostMapping("/setnewpassword")
    public String submitChooseNewPasswordForm(@ModelAttribute("choosePasswordForm") @Valid ChoosePasswordForm choosePasswordForm,
                                          BindingResult result, Errors errors, @ActiveUser CustomUserDetails userDetails,
                                          Model model){
        if(result.hasErrors()){
            LOGGER.info("There was an error on the choose new password form");
            errors.getAllErrors().stream().forEach(e -> LOGGER.info(e.toString()));
            return "pages/registration/choosePassword";
        }else {
            LOGGER.info("Choose new Password form submitted successfully");
            userDetailsService.changePassword(userDetails.getUser(), choosePasswordForm);
            model.addAttribute("name", userDetails.getFullName());
            SecurityContextHolder.clearContext();
            LOGGER.info("The User was logged out");
            return "pages/registration/choosePasswordSuccess";
        }
    }

}
