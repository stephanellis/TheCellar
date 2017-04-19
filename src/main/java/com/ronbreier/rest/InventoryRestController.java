package com.ronbreier.rest;

import com.ronbreier.entities.Beer;
import com.ronbreier.entities.User;
import com.ronbreier.entities.UserBeerLink;
import com.ronbreier.repositories.BeerRepository;
import com.ronbreier.repositories.UserBeerLinkRepository;
import com.ronbreier.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Created by ron.breier on 4/14/2017.
 */

@RestController
@RequestMapping("/rest/inventory/{userId}")
public class InventoryRestController {

    private static final Logger LOGGER = Logger.getLogger(InventoryRestController.class);

    @Autowired
    private UserBeerLinkRepository userBeerLinkRepository;

    @GetMapping
    public DataTablesResponse<UserBeerLink> getInvertoryForUser(@PathVariable("userId") Long userId){
        LOGGER.info("Getting inventory for userId " + userId );
        DataTablesResponse<UserBeerLink> inventory = new DataTablesResponse<>(userBeerLinkRepository.findByUserId(userId));
        LOGGER.info("User " + userId  + " has " + inventory.getData().size() + " beers.");
        return inventory;
    }
}
