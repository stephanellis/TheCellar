package com.ronbreier.forms;

import com.ronbreier.annotations.FieldMatch;
import com.ronbreier.annotations.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ron Breier on 4/10/2017.
 */

@FieldMatch.List({
    @FieldMatch(first = "password", second = "passwordAgain", message = "the password must be entered twice")
})
public class UserRegistrationForm {

    @NotNull
    @NotEmpty
    @Size(min=1, max=50)
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=1, max=50)
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min=1, max=50)
    private String lastName;

    @NotNull
    @NotEmpty
    @Size(min=6, max=50)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min=6, max=50)
    private String passwordAgain;

    @NotNull
    @NotEmpty@Size(min=7, max=15)
    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
