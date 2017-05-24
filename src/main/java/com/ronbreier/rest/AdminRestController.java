package com.ronbreier.rest;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.entities.User;
import com.ronbreier.entities.UserBeerLink;
import com.ronbreier.security.CustomUserDetails;
import com.ronbreier.services.InventoryService;
import com.ronbreier.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ron Breier on 5/19/2017.
 * Rest Controller for Admin Functions
 */

@RestController
@RequestMapping("/rest/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminRestController {

    private static final Logger LOGGER = Logger.getLogger(AdminRestController.class);

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/beers")
    public DataTablesResponse<UserBeerLink> getAllBeers(@ActiveUser CustomUserDetails userDetails){
        LOGGER.info("Getting all Beers for admin User" + userDetails.getUsername());
        DataTablesResponse<UserBeerLink> allBeers = inventoryService.getAllBeersForTable();
        return allBeers;
    }

    @GetMapping("/users")
    public DataTablesResponse<CustomUserDetails> getAllUser(@ActiveUser CustomUserDetails userDetails){
        LOGGER.info("Getting all Users under active user's access roles " + userDetails.getUserRolesString() + " for admin User" + userDetails.getUsername());
        DataTablesResponse<CustomUserDetails> allUsers  = userService.getAllUsersForTable();
        return allUsers;
    }

}
