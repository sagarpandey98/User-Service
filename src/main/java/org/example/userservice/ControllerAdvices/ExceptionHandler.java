package org.example.userservice.ControllerAdvices;

import org.example.userservice.exception.IncorrectPasswordException;
import org.example.userservice.exception.MailAlreadyExistException;
import org.example.userservice.exception.TokenNotFoundException;
import org.example.userservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handlingUserNotFoundException(UserNotFoundException userNotFoundException){
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> handlingIncorrectPasswordException(IncorrectPasswordException incorrectPasswordException){
        return new ResponseEntity<>(incorrectPasswordException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(MailAlreadyExistException.class)
    public ResponseEntity<String> handlingmailAlreadyExistException(MailAlreadyExistException mailAlreadyExistException) {
        return new ResponseEntity<>(mailAlreadyExistException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<String> handlingTokenNotFoundException(TokenNotFoundException tokenNotFoundException){
        return new ResponseEntity<>(tokenNotFoundException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
