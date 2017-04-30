package com.ronbreier.forms;

import com.ronbreier.annotations.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ron.breier on 4/28/2017.
 */
public class ResetPasswordForm {

    @NotNull
    @NotEmpty
    @Size(min=1, max=50)
    @ValidEmail
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
