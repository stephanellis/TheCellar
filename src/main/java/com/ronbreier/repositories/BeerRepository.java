package com.ronbreier.repositories;

import com.ronbreier.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ron.breier on 4/13/2017.
 */

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {



}
