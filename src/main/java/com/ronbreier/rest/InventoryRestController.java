package com.ronbreier.rest;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.entities.Beer;
import com.ronbreier.entities.User;
import com.ronbreier.entities.UserBeerLink;
import com.ronbreier.forms.AddBeerForm;
import com.ronbreier.security.CustomUserDetails;
import com.ronbreier.services.InventoryService;
import com.ronbreier.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by Ron Breier on 4/14/2017.
 * Rest Controller for Inventory functionality
 */

@RestController
@RequestMapping("/rest/inventory")
public class InventoryRestController {

    private static final Logger LOGGER = Logger.getLogger(InventoryRestController.class);

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public DataTablesResponse<UserBeerLink> getInvertoryForUser(@ActiveUser CustomUserDetails userDetails){
        LOGGER.info("Getting inventory for userId " + userDetails.getUserId() );
        DataTablesResponse<UserBeerLink> inventory = inventoryService.getInventoryForTable(userDetails.getUserId());
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
            inventoryService.saveNewUserBeerLink(userService.getUpToDateUser(userDetails.getUserId()), form);
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
            inventoryService.saveEditedUserBeerLink(linkID, form);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    @PostMapping("/delete/item")
    public void deleteInventoryItem(@ActiveUser CustomUserDetails userDetails, HttpServletResponse response,
                @RequestParam("user_beer_link_id") Long beerLinkIdToDelete){
        LOGGER.info("Deleting inventory item for user " + userDetails.getUserId() );
        LOGGER.info("Deleting Beer Link ID " + beerLinkIdToDelete);
        UserBeerLink ubl = inventoryService.getUblById(beerLinkIdToDelete);
        if (ubl == null || !ubl.getUser().equals(userDetails.getUser())){
            LOGGER.info("Something went wrong");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else{
            LOGGER.info("Found User Beer Link " + ubl);
            inventoryService.deleteUserBeerLink(ubl);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @GetMapping("/find/item/{link_id}")
    public UserBeerLink getInventoryItem(@ActiveUser CustomUserDetails userDetails,
                 @PathVariable("link_id") Long linkId){
        return inventoryService.getUblById(linkId);
    }

}

