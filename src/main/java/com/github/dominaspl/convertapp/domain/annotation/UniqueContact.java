package com.github.dominaspl.convertapp.domain.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueContactValidatorImpl.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueContact {

    String message() default "Contact is already in database";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
