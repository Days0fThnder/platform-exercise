package com.jeanleman.userAuthFender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
@Getter
@Setter
public class UserApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public UserApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
}
