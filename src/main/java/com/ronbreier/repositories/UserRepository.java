package com.ronbreier.repositories;

import com.ronbreier.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Ron Breier on 4/7/2017.
 * Repository for User Object
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(@Param("username")String username);

}