package com.ronbreier.repositories;

import com.ronbreier.entities.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Ron Breier on 4/12/2017.
 */

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    EmailVerification findByUserId(@Param("userId") Long userId);

    EmailVerification findByToken(@Param("token") String token);

    @Query("Select e from EmailVerification e where e.dateGenerated < cutoff")
    List<EmailVerification> findExpired(@Param("cutoff")Timestamp cutoff);
}
