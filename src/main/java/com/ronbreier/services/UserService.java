package com.ronbreier.services;

import com.ronbreier.entities.User;
import com.ronbreier.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ron Breier on 5/3/2017.
 * Service Class to perorm User Operations
 */

@Service
public class UserService {

    private final static Logger LOGGER = Logger.getLogger(UserService.class);

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

}
