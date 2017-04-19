package com.ronbreier.repositories;

import com.ronbreier.entities.UserBeerLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Ron Breier on 4/19/2017.
 * Repository for UserBeerLinks
 */

@Repository
public interface UserBeerLinkRepository extends JpaRepository<UserBeerLink, Long> {

    @Query("select ubl from UserBeerLink ubl where ubl.user.userId = :userId")
    List<UserBeerLink> findByUserId(@Param("userId")Long userId);

}
