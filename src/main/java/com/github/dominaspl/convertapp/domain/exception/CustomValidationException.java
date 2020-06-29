package com.github.dominaspl.convertapp.domain.exception;

import com.github.dominaspl.convertapp.domain.enumeration.ValidationExceptionKey;

public class CustomValidationException extends RuntimeException {

    public CustomValidationException(String field, ValidationExceptionKey key) {
        super(field + " - " + key.toString());
    }
}
