package com.william.createwebservice.exception;

public class UserServiceException extends RuntimeException{
    private static final long serialVersionUID = -1421683760830696826L;

    public UserServiceException(String message) {
        super(message);
    }
}
