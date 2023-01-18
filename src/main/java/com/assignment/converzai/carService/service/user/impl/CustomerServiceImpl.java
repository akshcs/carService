package com.assignment.converzai.carService.service.user.impl;

import com.assignment.converzai.carService.service.user.impl.UserServiceImpl;
import com.assignment.converzai.carService.service.user.intf.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("customer")
public class CustomerServiceImpl extends UserServiceImpl implements CustomerService {
}
