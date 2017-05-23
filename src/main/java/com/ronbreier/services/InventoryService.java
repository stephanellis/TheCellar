package com.ronbreier.services;

import com.ronbreier.entities.Beer;
import com.ronbreier.entities.User;
import com.ronbreier.entities.UserBeerLink;
import com.ronbreier.forms.AddBeerForm;
import com.ronbreier.repositories.BeerRepository;
import com.ronbreier.repositories.UserBeerLinkRepository;
import com.ronbreier.repositories.UserRepository;
import com.ronbreier.rest.DataTablesResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ron Breier on 5/3/2017.
 * Service Class to handle inventory operations
 */

@Service
public class InventoryService {

    private static final Logger LOGGER = Logger.getLogger(InventoryService.class);

    @Autowired
    private UserBeerLinkRepository userBeerLinkRepository;

    @Autowired
    private BeerRepository beerRepository;

    public DataTablesResponse<UserBeerLink> getInventoryForTable(Long userId) {
        return new DataTablesResponse<>(userBeerLinkRepository.findByUserId(userId));
    }

    public DataTablesResponse<UserBeerLink> getAllBeersForTable() {
        return new DataTablesResponse<>(userBeerLinkRepository.findAll());
    }

    public void saveNewUserBeerLink(User user, AddBeerForm form){
        LOGGER.info("Saving new link " + user + form);
        Beer beer = beerRepository.save(new Beer(form));
        userBeerLinkRepository.save(new UserBeerLink(user, beer, form.getCount()));
        LOGGER.info("Saved user beer link ");
    }

    public void saveEditedUserBeerLink(Long linkID, AddBeerForm form){
        LOGGER.info("Saving edited link " + linkID + form);
        UserBeerLink ubl = getUblById(linkID);
        ubl.editWithForm(form);
        userBeerLinkRepository.save(ubl);
        LOGGER.info("Saved changes to UBL " + ubl);
    }

    public UserBeerLink getUblById(Long linkID){
        return userBeerLinkRepository.findOne(linkID);
    }

    public void deleteUserBeerLink(UserBeerLink ubl){
        userBeerLinkRepository.delete(ubl);
        LOGGER.info("Deleted ubl " + ubl);
    }
}
