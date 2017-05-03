package com.ronbreier.services;

import com.ronbreier.entities.User;
import com.ronbreier.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ron Breier on 5/3/2017.
 * Service Class to perorm User Operations
 */

@Service
public class UserService {

   @Autowired
   private UserRepository userRepository;

    public User getUpToDateUser(Long userId) {
        return userRepository.findOne(userId);
    }

}
