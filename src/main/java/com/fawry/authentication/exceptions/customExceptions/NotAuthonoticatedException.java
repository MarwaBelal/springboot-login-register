package com.fawry.authentication.exceptions.customExceptions;

public class NotAuthonoticatedException extends RuntimeException {

    public NotAuthonoticatedException() {
    }

    public NotAuthonoticatedException(String message) {
        super(message);
    }

    public NotAuthonoticatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
