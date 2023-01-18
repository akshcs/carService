package com.assignment.converzai.carService.service.validation.impl;

import com.assignment.converzai.carService.exception.user.DuplicateUserException;
import com.assignment.converzai.carService.service.user.intf.UserService;
import com.assignment.converzai.carService.service.validation.intf.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserValidationServiceImpl implements UserValidationService {

    @Autowired
    @Qualifier("user")
    UserService userService;
    @Override
    public void checkDuplicates(String userName, String email, String mobile) throws DuplicateUserException {
        if(Objects.nonNull(userName)){
            checkDuplicateUserName(userName);
        }
        if(Objects.nonNull(email)){
            checkDuplicateEmail(email);
        }
        if(Objects.nonNull(mobile)){
            checkDuplicateMobile(mobile);
        }
    }

    private void checkDuplicateUserName(String userName) throws DuplicateUserException {
        try{
            if(Objects.nonNull(userService.getUserByUsername(userName))) {
                throw new RuntimeException("UserName already Exists , Please Choose a different UserName");
            }
        } catch (UsernameNotFoundException e) {
        }
    }
    private void checkDuplicateEmail(String email) throws DuplicateUserException {
        try{
            if(Objects.nonNull(userService.getUserByEmail(email))) {
                throw new DuplicateUserException("Email already Exists , Please Choose a different Email");
            }
        } catch (UsernameNotFoundException e) {
        }

    }
    private void checkDuplicateMobile(String mobile) throws DuplicateUserException {
        try{
            if(Objects.nonNull(userService.getUserByEmail(mobile))) {
                throw new RuntimeException("Mobile Number already Exists , Please Choose a different Mobile Number");
            }
        } catch (UsernameNotFoundException e) {
        }

    }
}
