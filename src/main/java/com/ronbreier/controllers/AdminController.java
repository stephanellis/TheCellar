package com.ronbreier.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ron.breier on 5/10/2017.
 */

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private static final Logger LOGGER = Logger.getLogger(AdminController.class);

    @GetMapping
    public String getAdminConsole(){
        LOGGER.info("Accessed Admin Console");
        return "pages/admin/admin";
    }

}
