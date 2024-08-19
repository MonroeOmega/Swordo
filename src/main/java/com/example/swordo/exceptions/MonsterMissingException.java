package com.example.swordo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Monster already killed.")
public class MonsterMissingException extends RuntimeException{
    public MonsterMissingException(){
        super("The monster is already killed.");
    }
}
