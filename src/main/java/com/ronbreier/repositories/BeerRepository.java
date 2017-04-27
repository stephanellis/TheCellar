package com.ronbreier.repositories;

import com.ronbreier.entities.Beer;
import com.ronbreier.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ron.breier on 4/13/2017.
 */

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {



}
