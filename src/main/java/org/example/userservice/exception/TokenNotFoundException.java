package org.example.userservice.exception;

public class TokenNotFoundException extends Exception{
    public TokenNotFoundException(String message){
        super(message);
    }
}
