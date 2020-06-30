package com.github.dominaspl.convertapp.domain.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConstraintValidationError {

    private String time;
    private String message;
    private String status;

    public ConstraintValidationError(LocalDateTime time, String message) {
        this.time = parseTime(time);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST.toString();
    }

    public String getMessage() {
        return message;
    }

    private String parseTime(LocalDateTime time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }
}
