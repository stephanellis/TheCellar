package com.ronbreier.repositories;

import com.ronbreier.entities.Beer;
import com.ronbreier.entities.User;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Ron Breier on 4/18/2017.
 * Testing Class to test Beer Repository
 */

@RunWith(SpringRunner.class)
@ActiveProfiles
@SpringBootTest
public class BeerRepositoryTests {

    private final static Logger LOGGER = Logger.getLogger(BeerRepositoryTests.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BeerRepository beerRepository;

    @Test
    public void retrieveInventoryForUser() {
        User user = userRepository.getOne(1L);
        LOGGER.info("Retrieved User " + user);
        List<Beer> beers = beerRepository.findBeersByUsers(user);
        LOGGER.info("Retrieved " + beers + " Beers" );
    }

}
