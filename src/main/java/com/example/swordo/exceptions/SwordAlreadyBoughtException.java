package com.example.swordo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.NOT_FOUND, reason = "Sword is already bought by someone.")
public class SwordAlreadyBoughtException extends RuntimeException{
    public SwordAlreadyBoughtException(){
        super("Sword already bought.");
    }
}
