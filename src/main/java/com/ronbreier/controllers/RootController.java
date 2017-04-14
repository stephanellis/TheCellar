package com.ronbreier.controllers;

import com.ronbreier.entities.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ron Breier on 4/6/2017.
 * Project Root Controller to enter the project
 */

@Controller
@RequestMapping("/")
public class RootController {

    private static final Logger LOGGER = Logger.getLogger(RootController.class.getName());

    @GetMapping
    public String enterProject(){
        LOGGER.info("Root Accessed");
        return "pages/welcome/index";
    }

    @GetMapping("login")
    public String getLogin(){
        return "pages/login/login";
    }

}
