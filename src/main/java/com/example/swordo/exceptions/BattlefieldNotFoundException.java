package com.example.swordo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Battlefield not found.")
public class BattlefieldNotFoundException extends RuntimeException{
    public BattlefieldNotFoundException(){
        super("The battlefield you are trying to access does not exist, you cheeky ass");
    }
}
