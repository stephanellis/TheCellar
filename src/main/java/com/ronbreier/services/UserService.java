package com.ronbreier.services;

import com.ronbreier.entities.User;
import com.ronbreier.repositories.UserRepository;
import com.ronbreier.repositories.UserRolesRepository;
import com.ronbreier.rest.DataTablesResponse;
import com.ronbreier.security.CustomUserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ron Breier on 5/3/2017.
 * Service Class to perorm User Operations
 */

@Service
public class UserService {

    private final static Logger LOGGER = Logger.getLogger(UserService.class);

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserRepository userRepository;

    public User getUpToDateUser(Long userId) {
        return userRepository.findOne(userId);
    }

    public void saveUser(User user){
        userRepository.save(user);
        LOGGER.info("Saved user " + user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public DataTablesResponse<CustomUserDetails> getUsersAndRolesWithLowerRoleForTable(CustomUserDetails userDetails){
        List<User> usersWithLesserRoles = userRepository.findAll();
        String topRole = "ROLE_ADMIN";
        if (userDetails.getUserRolesString().contains("ROLE_SUPER")){
            topRole = "ROLE_SUPER";
        }
        List<CustomUserDetails> usersWithRoles = new ArrayList<>();
        for (User u: usersWithLesserRoles.stream().distinct().collect(Collectors.toList())){
            List<String> roles = userRolesRepository.findRoleByUsername(u.getUsername());
            if (!roles.contains(topRole)){
                usersWithRoles.add(new CustomUserDetails(u, roles));
            }
        }
        return new DataTablesResponse<>(usersWithRoles);
    }

}
