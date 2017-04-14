package com.ronbreier.repositories;

import com.ronbreier.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ron Breier on 4/7/201
 * Repository for UserRole Object
 */

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole, Long> {

    @Query("select a.role from UserRole a, User b where b.username=?1 and a.userId=b.userId")
    List<String> findRoleByUsername(String username);

}