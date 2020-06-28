package com.github.dominaspl.convertapp.domain.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidatorImpl.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeValidation {

    String message() default "Incorrect age given";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
