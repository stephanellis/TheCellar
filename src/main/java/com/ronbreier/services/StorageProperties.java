package com.ronbreier.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Ron Breier on 4/26/2017.
 * Class Maintains Properties for local storage
 */

@ConfigurationProperties("storage")
public class StorageProperties {

    @Value("${spring.paths.storagelocation}")
    private String location;

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){

        this.location= location;
    }

}