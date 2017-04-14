package com.ronbreier.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Ron Breier on 4/7/2017.
 */

@Entity
@Table(name="user_roles")
public class UserRole implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_role_id", nullable=false)
    private Long userRoleId;

    @Column(name="userId", nullable=false)
    private Long userId;

    @Column(name="role", nullable=false)
    private String role;

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserRole(){
        // Zero Arg Constructor to satisfy JPA
    }

    public UserRole(User user){
        this.userId = user.getUserId();
        this.role = "ROLE_USER";
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "userRoleId=" + userRoleId +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                '}';
    }

}