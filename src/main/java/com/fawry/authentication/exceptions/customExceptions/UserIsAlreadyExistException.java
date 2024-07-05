package com.fawry.authentication.exceptions.customExceptions;

public class UserIsAlreadyExistException extends RuntimeException {

    public UserIsAlreadyExistException() {
    }

    public UserIsAlreadyExistException(String message) {
        super(message);
    }

    public UserIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
