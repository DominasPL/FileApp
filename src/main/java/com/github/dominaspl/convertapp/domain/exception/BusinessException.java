package com.github.dominaspl.convertapp.domain.exception;

import com.github.dominaspl.convertapp.domain.enumeration.BusinessExceptionKey;

public class BusinessException extends RuntimeException {

    public BusinessException(BusinessExceptionKey key) {
        super(key.toString());
    }

}
