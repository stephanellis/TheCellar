package com.ronbreier.services;

import com.ronbreier.entities.User;
import com.ronbreier.repositories.UserRepository;
import com.ronbreier.repositories.UserRolesRepository;
import com.ronbreier.security.CustomUserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

}