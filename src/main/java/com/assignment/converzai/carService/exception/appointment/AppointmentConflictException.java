package com.assignment.converzai.carService.exception.appointment;

public class AppointmentConflictException extends RuntimeException{
    public AppointmentConflictException(String s){
        super(s);
    }
}
