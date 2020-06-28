package com.github.dominaspl.convertapp.web.response;

import org.springframework.http.HttpStatus;

public class CustomResponseClass {

    private String message;
    private String status;

    public CustomResponseClass(String message) {
        this.message = message;
        this.status = HttpStatus.OK.toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
