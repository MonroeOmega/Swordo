package com.example.swordo.exceptions;

public class DeathException extends RuntimeException{
    //Note: With this I don't have to put logic in the FightController.

    public DeathException() {
        super("You have died.");
    }
}
