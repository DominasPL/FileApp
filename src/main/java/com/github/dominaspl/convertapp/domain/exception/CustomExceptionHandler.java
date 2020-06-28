package com.github.dominaspl.convertapp.domain.exception;


import com.github.dominaspl.convertapp.web.error.ConstraintValidationError;
import com.github.dominaspl.convertapp.web.error.HttpMediaTypeError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ConstraintValidationError>> handleAnyException(ConstraintViolationException ex) {

        List<ConstraintValidationError> responseErrors = new ArrayList<>();

        for (ConstraintViolation violation : ex.getConstraintViolations()){
            ConstraintValidationError customError = new ConstraintValidationError(LocalDateTime.now(),
                    violation.getMessage());
            responseErrors.add(customError);
        }

        return new ResponseEntity<>(responseErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpMediaTypeError error = new HttpMediaTypeError(ex.getMessage() + ". XML or text is only accepted");
        return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
