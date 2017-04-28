package com.ronbreier.controllers;

import com.ronbreier.services.FileSystemStorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ron Breier on 4/25/2017.
 * Controller returns uncached images to prevent
 * long load times for large and static
 * images like the background image
 */

@Controller
@RequestMapping("/uncached/images")
public class CachableImageController {

    @Autowired
    private FileSystemStorageService storageService;

    private static final Logger LOGGER = Logger.getLogger(CachableImageController.class.getName());

    @GetMapping(value = "/{file_name}",produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody Resource getBackgroundImage(@PathVariable("file_name") String path){
        path += ".jpg";
        LOGGER.info("Retrieving a portfolio image with the name " + path);
        return storageService.loadAsResource(path);
    }
}
