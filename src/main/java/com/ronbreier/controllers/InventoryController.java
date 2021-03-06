package com.ronbreier.controllers;

import com.ronbreier.annotations.ActiveUser;
import com.ronbreier.forms.AddBeerForm;
import com.ronbreier.repositories.UserBeerLinkRepository;
import com.ronbreier.security.CustomUserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ron Breier on 4/13/2017.
 */

@Controller
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger LOGGER = Logger.getLogger(InventoryController.class);

    @Autowired
    private UserBeerLinkRepository userBeerLinkRepository;

    @GetMapping
    public String getInventoryPage(@ActiveUser CustomUserDetails userDetails, Model model){
        LOGGER.info("Navigating to the Inventory Page for " + userDetails.getUsername());
        model.addAttribute("hasInventory", !userBeerLinkRepository.findByUserId(userDetails.getUserId()).isEmpty());
        model.addAttribute("addBeerForm", new AddBeerForm());
        return "pages/user/inventory";
    }

}
