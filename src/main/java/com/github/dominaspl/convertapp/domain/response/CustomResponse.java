package com.github.dominaspl.convertapp.domain.response;

import org.springframework.http.HttpStatus;

public class CustomResponse {

    private String message;
    private String status;

    public CustomResponse(String message) {
        this.message = message;
        this.status = HttpStatus.CREATED.toString();
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

}
