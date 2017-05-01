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
            User thisUser = userRepository.findOne(userDetails.getUserId());
            if (isBeerInDb(newBeer)){
                newBeer = beerRepository.findByBrewerAndNameAndYear(form.getBrewer(), form.getBeerName(), form.getYear());
                LOGGER.info("This beer is already in the DB " + newBeer);
            }else {
                beerRepository.save(newBeer);
                LOGGER.info("Saved new beer " + newBeer);
            }
            LOGGER.info("Linking to user " + thisUser);
            UserBeerLink ubl = new UserBeerLink(thisUser, newBeer, form.getCount());
            userBeerLinkRepository.save(ubl);
            LOGGER.info("Saved new user beer link " + ubl);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    @PostMapping("/edit/item/{link_id}")
    public void editInventoryItem(@ActiveUser CustomUserDetails userDetails,
                                 @ModelAttribute("userRegForm") @Valid AddBeerForm form, BindingResult result, Errors errors,
                                 HttpServletResponse response, @PathVariable("link_id")Long linkID){
        LOGGER.info("Posting edited inventory item for userId " + userDetails.getUserId());
        LOGGER.info("Editing Link " + linkID);
        if(result.hasErrors()){
            LOGGER.info("There were errors on the add beer form");
            errors.getAllErrors().stream().forEach(e -> LOGGER.info(e.toString()));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }else {
            UserBeerLink ublEditing = userBeerLinkRepository.findOne(linkID);
            ublEditing.editBeer(form);
            userBeerLinkRepository.save(ublEditing);
            LOGGER.info("Saved edited user beer link " + ublEditing);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    @PostMapping("/delete/item")
    public void deleteInventoryItem(@ActiveUser CustomUserDetails userDetails, HttpServletResponse response,
                @RequestParam("user_beer_link_id") Long beerLinkToDelete){
        LOGGER.info("Deleting inventory item for user " + userDetails.getUserId() );
        LOGGER.info("Deleting Beer Link ID " + beerLinkToDelete);
        UserBeerLink ubl = userBeerLinkRepository.findOne(beerLinkToDelete);
        if (ubl == null || !ubl.getUser().equals(userDetails.getUser())){
            LOGGER.info("Something went wrong");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else{
            LOGGER.info("Found User Beer Link " + ubl);
            userBeerLinkRepository.delete(ubl);
            LOGGER.info("Deleted User Beer Link " + ubl);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @GetMapping("/find/item/{link_id}")
    public UserBeerLink getInventoryItem(@ActiveUser CustomUserDetails userDetails,
                 @PathVariable("link_id") Long linkId){
        LOGGER.info("Getting invintory item with link " + linkId);
        return userBeerLinkRepository.findOne(linkId);
    }

    private boolean isBeerInDb(Beer beer){
        return (beerRepository.findByBrewerAndNameAndYear(beer.getBrewer(),beer.getName(), beer.getYear()) != null);
    }

}

