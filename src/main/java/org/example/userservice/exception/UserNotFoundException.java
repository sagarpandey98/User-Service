package org.example.userservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends Exception{
    private String detail;
    public UserNotFoundException(String message){
        super(message);
    }
}
