package com.github.dominaspl.convertapp.domain.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomErrorClass {

    private String time;
    private String message;
    private String status;

    public CustomErrorClass(LocalDateTime time, String message) {
        this.time = parseTime(time);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String parseTime(LocalDateTime time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }
}
