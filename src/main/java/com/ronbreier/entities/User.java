package com.ronbreier.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ronbreier.forms.UserRegistrationForm;
import org.apache.commons.lang.WordUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    @JsonProperty("enabled")
    private int enabled;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserBeerLink> userBeerLinks = new ArrayList<>();

    @JsonIgnore
    @Column(name="password_reset")
    private boolean passwordReset;

    @JsonIgnore
    @Column(name="date_created")
    private Timestamp dateCreated;

    public boolean isPasswordReset() {
        return passwordReset;
    }

    public void setPasswordReset(boolean passwordReset) {
        this.passwordReset = passwordReset;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

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

    public String getFormattedPhoneNumber(){
        return this.phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)","($1) $2-$3");
    }

    public List<UserBeerLink> getUserBeerLinks() {
        return userBeerLinks;
    }

    public void setUserBeerLinks(List<UserBeerLink> userBeerLinks) {
        this.userBeerLinks = userBeerLinks;
    }

    public void addUserBeerLink(UserBeerLink userBeerLink){
        this.userBeerLinks.add(userBeerLink);
    }

    public void addUserBeerLinks(List<UserBeerLink> userBeerLinks){
        this.userBeerLinks.addAll(userBeerLinks);
    }

    public User(){
        // Zero Argument Constructor to satisfy JPA
    }

    public User(UserRegistrationForm userRegistrationForm, PasswordEncoder passwordEncoder){
        this.username = userRegistrationForm.getEmail();
        this.firstName = userRegistrationForm.getFirstName().toUpperCase();
        this.lastName = userRegistrationForm.getLastName().toUpperCase();
        this.phoneNumber = userRegistrationForm.getPhoneNumber();
        this.password = passwordEncoder.encode(userRegistrationForm.getPassword());
        this.setLoginCount(0);
        this.setEnabled(0);
        this.passwordReset = false;
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
        this.passwordReset = user.passwordReset;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
