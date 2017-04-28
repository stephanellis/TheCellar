package com.ronbreier.services;

import org.springframework.core.io.Resource;

import java.nio.file.Path;

/**
 * Created by Ron Breier on 4/26/2017.
 * Storage Service to be implemented
 */

public interface StorageService {

    void init();

    Path load(String filename);

    Resource loadAsResource(String filename);

}
