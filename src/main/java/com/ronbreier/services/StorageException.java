package com.ronbreier.services;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;

/**
 * Created by ron.breier on 3/20/2017.
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}