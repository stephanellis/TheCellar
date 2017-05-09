package com.ronbreier.annotations;

import com.ronbreier.validators.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

;

/**
 * Created by Ron Breier on 4/30/2017.
 * Annotation to validate a phone number 10 digits
 */

@Target({TYPE, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface ValidPhoneNumber {

    String message() default "Phone Number must be 10 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
