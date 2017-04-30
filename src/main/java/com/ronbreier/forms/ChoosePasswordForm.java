package com.ronbreier.forms;

import com.ronbreier.annotations.FieldMatch;
import com.ronbreier.annotations.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ron Breier on 4/29/2017.
 * Form to validate new password selections
 */

@FieldMatch.List({
    @FieldMatch(first = "password", second = "passwordAgain", message = "The password must be entered twice")
})
public class ChoosePasswordForm {

    @NotNull
    @NotEmpty
    @Size(min=6, max=50)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min=6, max=50)
    private String passwordAgain;

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
}
