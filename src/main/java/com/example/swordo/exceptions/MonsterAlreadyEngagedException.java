package com.example.swordo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Monster already engaged.")
public class MonsterAlreadyEngagedException extends RuntimeException{
    public MonsterAlreadyEngagedException(){
        super("Monster already engaged.");
    }
}
