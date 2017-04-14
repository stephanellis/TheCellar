package com.ronbreier.controllers;

import com.ronbreier.entities.User;
import com.ronbreier.entities.UserRole;
import com.ronbreier.forms.UserRegistrationForm;
import com.ronbreier.repositories.UserRepository;
import com.ronbreier.repositories.UserRolesRepository;
import com.ronbreier.services.EmailService;
import com.ronbreier.services.EmailVerificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by Ron Breier on 4/10/2017.
 */

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final Logger LOGGER = Logger.getLogger(DashboardController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @GetMapping()
    public String getRegistration(Model model){
        LOGGER.info("Accessed Registration Page");
        model.addAttribute("userRegForm", new UserRegistrationForm());
        return "pages/registration/registration";
    }

    @PostMapping
    public String submitRegistration(@ModelAttribute("userRegForm") @Valid UserRegistrationForm userRegistrationForm,
            BindingResult result, Errors errors, Model model){
        if(result.hasErrors()){
            LOGGER.info("There were errors on the registration form");
            errors.getAllErrors().stream().forEach(e -> LOGGER.info(e.toString()));
            userRegistrationForm.setPassword("");
            userRegistrationForm.setPasswordAgain("");
            return "pages/registration/registration";
        }else{
            LOGGER.info("Registration form submitted successfully");
            User newUser = new User();
            try {
                newUser = registerNewUserAccount(userRegistrationForm);
            } catch (Exception e) {
                LOGGER.error("Something went wrong registering the new user " + userRegistrationForm.getEmail() + " ", e);
                return "pages/registration/registrationError";
            }
            LOGGER.info("New user registered " + newUser);
            model.addAttribute("newUser", newUser);
            return "pages/registration/registrationSuccess";
        }
    }

    public User registerNewUserAccount(UserRegistrationForm userRegistrationForm)throws Exception{
        if(usernameExist(userRegistrationForm.getEmail())){
            throw new Exception("There is an account with the email: " +
                    userRegistrationForm.getEmail());
        }
        User newUser = new User(userRegistrationForm);
        // save user then get it back back from DB to get the ID
        userRepository.save(newUser);
        newUser = new User(userRepository.findByUsername(userRegistrationForm.getEmail()));
        // Create default role for new User Object
        UserRole newRole = new UserRole(newUser);
        userRolesRepository.save(newRole);
        // create unique api token to verify email
        emailVerificationService.generateVerificationUrl(newUser);
        // Send confirmation email to new user
        emailService.sendRegistrationEmail(newUser);
        return newUser;
    }

    private boolean usernameExist(String username){
        LOGGER.info("Checking to see if " + username + " is in the DB");
        User user = userRepository.findByUsername(username);
        if (user != null){
            LOGGER.info("Username: " + username + " is already in the DB");
            return true;
        }
        LOGGER.info("Username: " + username + " is not in the DB");
        return false;
    }

}
