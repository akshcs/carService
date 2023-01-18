package com.assignment.converzai.carService.service.user.impl;

import com.assignment.converzai.carService.service.user.intf.OperatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("operator")
public class OperatorServiceImpl extends UserServiceImpl implements OperatorService{
}
