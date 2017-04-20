package com.ronbreier.rest;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.entities.Beer;
import com.ronbreier.entities.User;
import com.ronbreier.entities.UserBeerLink;
import com.ronbreier.forms.AddBeerForm;
import com.ronbreier.repositories.BeerRepository;
import com.ronbreier.repositories.UserBeerLinkRepository;
import com.ronbreier.repositories.UserRepository;
import com.ronbreier.security.CustomUserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by ron.breier on 4/14/2017.
 */

@RestController
@RequestMapping("/rest/inventory")
public class InventoryRestController {

    private static final Logger LOGGER = Logger.getLogger(InventoryRestController.class);

    @Autowired
    private UserBeerLinkRepository userBeerLinkRepository;

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public DataTablesResponse<UserBeerLink> getInvertoryForUser(@ActiveUser CustomUserDetails userDetails){
        LOGGER.info("Getting inventory for userId " + userDetails.getUserId() );
        DataTablesResponse<UserBeerLink> inventory = new DataTablesResponse<>(userBeerLinkRepository.findByUserId(userDetails.getUserId()));
        LOGGER.info("User " + userDetails.getUserId()  + " has " + inventory.getData().size() + " beers.");
        return inventory;
    }

    @PostMapping("/add/item")
    public void addInventoryItem(@ActiveUser CustomUserDetails userDetails,
                 @ModelAttribute("userRegForm") @Valid AddBeerForm form, BindingResult result, Errors errors,
                 HttpServletResponse response){
        LOGGER.info("Posting new inventory item for userId " + userDetails.getUserId() );
        if(result.hasErrors()){
            LOGGER.info("There were errors on the add beer form");
            errors.getAllErrors().stream().forEach(e -> LOGGER.info(e.toString()));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }else {
            Beer newBeer = new Beer(form);
            beerRepository.save(newBeer);
            LOGGER.info("Saveed new beer " + newBeer);
            User thisUser = userRepository.findOne(userDetails.getUserId());
            LOGGER.info("Linking to user " + thisUser);
            UserBeerLink ubl = new UserBeerLink(thisUser, newBeer , form.getCount());
            userBeerLinkRepository.save(ubl);
            LOGGER.info("Saved new user beer link " + ubl);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }
}
