package com.ronbreier.entities;

/**
 * Created by ron.breier on 4/7/2017.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ronbreier.forms.UserRegistrationForm;
import org.apache.commons.lang.WordUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Ron Breier on 4/7/2017.
 */

@Entity
@Table(name="users")
public class User implements Serializable, Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable=false)
    @JsonProperty("userId")
    private Long userId;

    @Column(name="username", nullable=false, unique=true)
    @JsonProperty("username")
    private String username;

    @Column(name="first_name", nullable=false)
    @JsonProperty("firstName")
    private String firstName;

    @Column(name="last_name", nullable=false)
    @JsonProperty("lastName")
    private String lastName;

    @Column(name="phone_number", nullable=false)
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Column(name="password", nullable=false)
    @JsonIgnore
    private String password;

    @Column(name="login_count", nullable=false)
    @JsonProperty("loginCount")
    private Integer loginCount;

    @Column(name="enabled")
    @JsonIgnore
    private int enabled;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserBeerLink> userBeerLinks = new ArrayList<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getFullName(){
        return WordUtils.capitalizeFully(getFirstName() + " " + getLastName());
    }

    public List<UserBeerLink> getUserBeerLinks() {
        return userBeerLinks;
    }

    public void setUserBeerLinks(List<UserBeerLink> userBeerLinks) {
        this.userBeerLinks = userBeerLinks;
    }

    public User(){
        // Zero Argument Constructor to satisfy JPA
    }

    public User(UserRegistrationForm userRegistrationForm){
        this.username = userRegistrationForm.getEmail();
        this.firstName = userRegistrationForm.getFirstName().toUpperCase();
        this.lastName = userRegistrationForm.getLastName().toUpperCase();
        this.phoneNumber = userRegistrationForm.getPhoneNumber();
        this.password = userRegistrationForm.getPassword();
        this.setLoginCount(0);
        this.setEnabled(0);
    }

    public User(User user){
        this.userId = user.userId;
        this.username = user.username;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.password = user.password;
        this.enabled = user.enabled;
        this.phoneNumber = user.phoneNumber;
        this.loginCount = user.loginCount;
        this.userBeerLinks = user.userBeerLinks;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", loginCount=" + loginCount +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.getUsername().compareTo(((User)o).getUsername());
    }
}