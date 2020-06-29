package com.github.dominaspl.convertapp.domain.response;

import org.springframework.http.HttpStatus;

public class CustomResponse {

    private String message;
    private String status;

    public CustomResponse(String message) {
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
