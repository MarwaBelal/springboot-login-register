package com.fawry.authentication.exceptions.customExceptions;

public class NotAddedException extends RuntimeException {

    public NotAddedException() {
    }

    public NotAddedException(String message) {
        super(message);
    }

    public NotAddedException(String message, Throwable cause) {
        super(message, cause);
    }
}
