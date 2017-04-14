package com.ronbreier.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ron Breier on 4/6/2017.
 */

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private static final Logger LOGGER = Logger.getLogger(DashboardController.class);

    @GetMapping
    public String enterProject(){
        LOGGER.info("Dashboard Accessed");
        return "pages/user/dashboard";
    }
}
