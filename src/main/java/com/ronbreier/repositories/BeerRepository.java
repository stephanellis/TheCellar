package com.ronbreier.repositories;

import com.ronbreier.entities.Beer;
import com.ronbreier.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ron.breier on 4/13/2017.
 */
public interface BeerRepository extends JpaRepository<Beer, Long> {

    List<Beer> findBeersByUsers(@Param("users")User user);
}
