package org.example.userservice.exception;

public class MailAlreadyExistException extends Exception{
    public MailAlreadyExistException(String message){
        super(message);
    }

}
