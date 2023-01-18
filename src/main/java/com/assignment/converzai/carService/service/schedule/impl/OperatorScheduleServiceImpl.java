package com.assignment.converzai.carService.service.schedule.impl;

import com.assignment.converzai.carService.entity.TimePeriod;
import com.assignment.converzai.carService.entity.appointment.Appointment;
import com.assignment.converzai.carService.service.appointment.intf.AppointmentService;
import com.assignment.converzai.carService.service.schedule.AbstractScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("OperatorScheduleService")
public class OperatorScheduleServiceImpl extends AbstractScheduleServiceImpl {
    @Autowired
    private AppointmentService appointmentService;

    @Override
    public List<Appointment> getAppointmentsFromIdAndTime(long id, TimePeriod timePeriod) {
        return appointmentService.getAppointmentByOperatorAndTime(id, timePeriod.getStart(), timePeriod.getEnd());
    }
}
