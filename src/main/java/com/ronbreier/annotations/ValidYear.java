package com.ronbreier.annotations;

import com.ronbreier.validators.YearValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ron.breier on 4/30/2017.
 * Annotation to validate a 4 digit year
 */

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = YearValidator.class)
@Documented
public @interface ValidYear {

    String message() default "A Year must be 4 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
