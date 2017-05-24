package com.ronbreier.services;

import com.ronbreier.entities.User;
import com.ronbreier.entities.UserRole;
import com.ronbreier.forms.ChoosePasswordForm;
import com.ronbreier.forms.UserRegistrationForm;
import com.ronbreier.repositories.UserRepository;
import com.ronbreier.repositories.UserRolesRepository;
import com.ronbreier.security.CustomUserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by Ron Breier on 4/7/2017.
 * Service to Communicate with Spring security
 * and JPA Repos
 */

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(UserDetailsService.class);

    private final UserRepository userRepository;

    private final UserRolesRepository userRolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, UserRolesRepository userRolesRepository){
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Getting user with username: " + username);
        User user = userRepository.findByUsername(username);
        if (null == user) {
            LOGGER.info("No username " + username + " was found");
            throw new UsernameNotFoundException("No user with username: " + username);
        }else if (1 != user.getEnabled()){
            LOGGER.info(username + " has not completed registration");
            throw new UsernameNotFoundException("No user with username: " + username);
        }else {
            // Update the login count
            int i = user.getLoginCount();
            user.setLoginCount(++i);
            userRepository.save(user);
            LOGGER.info(username + " has logged in  " + i + " times");
            //Get the Roles and return them along with the user object
            List<String> userRoles = userRolesRepository.findRoleByUsername(username);
            return new CustomUserDetails(user, userRoles);
        }
    }

    public User registerNewUserAccount(UserRegistrationForm userRegistrationForm)throws Exception{
        if(usernameExist(userRegistrationForm.getEmail())){
            throw new Exception("There is an account with the email: " +
                    userRegistrationForm.getEmail());
        }
        User newUser = new User(userRegistrationForm, passwordEncoder);
        // save user then get it back back from DB to get the ID
        userRepository.save(newUser);
        newUser = new User(userRepository.findByUsername(userRegistrationForm.getEmail()));
        // Create default role for new User Object
        UserRole newRole = new UserRole(newUser);
        userRolesRepository.save(newRole);
        // test to see if this is the first user.  If so make it the super user.
        if(isFirstUser()){
            UserRole adminRole = new UserRole(newUser, "ROLE_ADMIN");
            userRolesRepository.save(adminRole);
            UserRole superRole = new UserRole(newUser, "ROLE_SUPER");
            userRolesRepository.save(superRole);
        }
        // create unique api token to verify email
        emailVerificationService.generateVerificationUrlUser(newUser, true);
        // Send confirmation email to new user
        emailService.sendRegistrationEmail(newUser, true);
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

    public User resetPassword(User user){
        String newPass = passwordGenerator();
        user.setPassword(passwordEncoder.encode(newPass));
        user.setPasswordReset(true);
        emailService.sendPasswordResetEmail(user, newPass);
        userRepository.save(user);
        return user;
    }

    public User changePassword(User user,  ChoosePasswordForm choosePasswordForm){
        user.setPasswordReset(false);
        user.setPassword(passwordEncoder.encode(choosePasswordForm.getPassword()));
        userRepository.save(user);
        LOGGER.info("Saved new password for " + user);
        return user;
    }

    private String passwordGenerator(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] specChars = "!@#$".toCharArray();
        char[] numbChars = "123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        for (int i = 0; i < 2; i++) {
            char c = specChars[random.nextInt(specChars.length)];
            sb.append(c);
        }
        for (int i = 0; i < 2; i++) {
            char c = numbChars[random.nextInt(numbChars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    private boolean isFirstUser(){
        return userRepository.count() == 1;
    }

}
