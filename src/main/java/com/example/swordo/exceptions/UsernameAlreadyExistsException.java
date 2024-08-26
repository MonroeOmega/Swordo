package com.example.swordo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already exists.")
public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(){
        super("Username already exists.");
    }
}
