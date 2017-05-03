package com.ronbreier.validators;

import com.ronbreier.annotations.ValidYear;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ron.breier on 4/30/2017.
 */
public class YearValidator implements ConstraintValidator<ValidYear, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String YEAR_PATTERN = "^[0-9]{4}$";

    @Override
    public void initialize(ValidYear constraintAnnotation) {
    }

    @Override
    public boolean isValid(String year, ConstraintValidatorContext context) {
        return (validateYear(year));
    }

    private boolean validateYear(String phoneNumber) {
        pattern = Pattern.compile(YEAR_PATTERN);
        matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

}
