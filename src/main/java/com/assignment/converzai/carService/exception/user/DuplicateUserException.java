package com.assignment.converzai.carService.exception.user;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String s) {
        super(s);
    }
}
