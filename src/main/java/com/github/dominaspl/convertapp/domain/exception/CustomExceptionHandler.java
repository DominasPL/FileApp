package com.github.dominaspl.convertapp.domain.exception;


import com.github.dominaspl.convertapp.domain.error.ConstraintValidationError;
import com.github.dominaspl.convertapp.domain.error.HttpMediaTypeError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ConstraintValidationError> handleCustomException(CustomValidationException ex) {

            ConstraintValidationError customError = new ConstraintValidationError(LocalDateTime.now(),
                    ex.getLocalizedMessage());

        return new ResponseEntity<>(customError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpMediaTypeError error = new HttpMediaTypeError(ex.getMessage() + ". XML or text is only accepted");
        return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


}
