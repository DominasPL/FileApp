package com.github.dominaspl.convertapp.domain.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnyException(ConstraintViolationException ex) {

        List<CustomErrorClass> responseErrors = new ArrayList<>();

        for (ConstraintViolation violation : ex.getConstraintViolations()){
            CustomErrorClass customError = new CustomErrorClass(LocalDateTime.now(),
                    violation.getMessage());
            responseErrors.add(customError);
        }

        return new ResponseEntity<>(responseErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
