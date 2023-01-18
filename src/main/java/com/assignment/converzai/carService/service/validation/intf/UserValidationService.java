package com.assignment.converzai.carService.service.validation.intf;

public interface UserValidationService {
    void checkDuplicates(String userName, String email, String mobile) throws RuntimeException;
}
