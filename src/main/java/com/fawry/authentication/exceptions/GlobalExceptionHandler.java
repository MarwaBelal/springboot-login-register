package com.fawry.authentication.exceptions;

import com.fawry.authentication.common.model.ErrorModel;
import com.fawry.authentication.exceptions.customExceptions.NotAuthenticatedException;
import com.fawry.authentication.exceptions.customExceptions.UserIsAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorModel> handleNotAuthenticatedException(
            NotAuthenticatedException recordNotFoundException) {

        ErrorModel responseError =
                ErrorModel.builder()
                        .message(recordNotFoundException.getMessage())
                        .occurredOn(new Timestamp(System.currentTimeMillis()))
                        .build();
        return new ResponseEntity<>(responseError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorModel> handleUserIsAlreadyExistException(
            UserIsAlreadyExistException userIsAlreadyExistException) {

        ErrorModel responseError =
                ErrorModel.builder()
                        .message(userIsAlreadyExistException.getMessage())
                        .occurredOn(new Timestamp(System.currentTimeMillis()))
                        .build();
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
