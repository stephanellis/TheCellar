package com.ronbreier.controllers;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.entities.Beer;
import com.ronbreier.repositories.BeerRepository;
import com.ronbreier.security.CustomUserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * Created by Ron Breier on 4/13/2017.
 */

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger LOGGER = Logger.getLogger(InventoryController.class);

    @Autowired
    private BeerRepository beerRepository;

    @GetMapping
    public String getInventoryPage(@ActiveUser CustomUserDetails userDetails, Model model){
        LOGGER.info("Navigating to the Inventory Page for " + userDetails.getUsername());
        List<Beer> inventory = beerRepository.findBeersByUsers(userDetails.getUser());
        LOGGER.info(inventory);
        model.addAttribute("inventory", inventory);
        return "pages/user/inventory";
    }

}
