package com.github.dominaspl.convertapp.domain.exception;

import com.github.dominaspl.convertapp.domain.enumeration.ValidationExceptionKey;
import com.github.dominaspl.convertapp.domain.error.ConstraintValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionHandlerTest {

    private CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    void setUp() {
        customExceptionHandler = new CustomExceptionHandler();
    }

    @Test
    void shouldReturnResponseEntityWithConstraintValidationErrorAndWith400StatusWhenCustomValidationExceptionIsThrown() {
        //when
        ResponseEntity<ConstraintValidationError> response = customExceptionHandler.handleCustomException(
                new CustomValidationException("Name", ValidationExceptionKey.NAME_IS_NOT_AVAILABLE));

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMessage());
        assertEquals("Name - NAME_IS_NOT_AVAILABLE", response.getBody().getMessage());
    }
}