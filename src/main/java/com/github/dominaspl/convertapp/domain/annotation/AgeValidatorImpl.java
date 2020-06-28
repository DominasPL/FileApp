package com.github.dominaspl.convertapp.domain.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgeValidatorImpl implements ConstraintValidator<AgeValidation, String> {


    @Override
    public void initialize(AgeValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String age, ConstraintValidatorContext constraintValidatorContext) {
        if (age == null || age.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile("\\d{1,3}");
        Matcher matcher = pattern.matcher(age);
        return matcher.matches();
    }
}
