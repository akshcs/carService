package com.assignment.converzai.carService.exception.exceptionHandler;

import com.assignment.converzai.carService.exception.appointment.AppointmentConflictException;
import com.assignment.converzai.carService.exception.user.DuplicateUserException;
import com.assignment.converzai.carService.exception.appointment.InvalidAppointmentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ DuplicateUserException.class })
    protected ResponseEntity<Object> handleDuplicateUser(DuplicateUserException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    protected ResponseEntity<Object> handleInvalidUser(UsernameNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ InvalidAppointmentException.class })
    protected ResponseEntity<Object> handleInvalidAppointment(InvalidAppointmentException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AppointmentConflictException.class })
    protected ResponseEntity<Object> handleAppointConflict(AppointmentConflictException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected  ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders httpHeaders,
                                                                   HttpStatus status, WebRequest request){
        return handleExceptionInternal(ex, ex.getBindingResult().getFieldError().getDefaultMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
