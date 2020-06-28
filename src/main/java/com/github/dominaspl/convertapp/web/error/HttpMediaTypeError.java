package com.github.dominaspl.convertapp.web.error;

import org.springframework.http.HttpStatus;

public class HttpMediaTypeError {

    private String message;
    private String status;

    public HttpMediaTypeError(String message) {
        this.message = message;
        this.status = HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString();
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
