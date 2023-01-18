package com.assignment.converzai.carService.rest.schedule;

import com.assignment.converzai.carService.entity.TimePeriod;
import com.assignment.converzai.carService.service.schedule.intf.ScheduleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/operatorSchedule")
public class OperatorScheduleResource {
    @Autowired
    @Qualifier("OperatorScheduleService")
    private ScheduleService operatorScheduleService;

    @GetMapping("/{operatorId}/appointments")
    @ApiOperation(value = "Finds all active Appointments for a operator in a Time Period",
            notes = "Provide an operator id to look all appointment for a operator in a time period from Appointment Table" +
                    "Time Period is Optional, It shows appointments for next 24 hours if Time Period is not passed")
    public List<TimePeriod> getAppointments(@PathVariable("operatorId") long operatorId, @Valid @RequestBody TimePeriod timePeriod)
            throws UsernameNotFoundException {
        return operatorScheduleService.getAppointmentsInTime(operatorId, timePeriod);
    }

    @GetMapping("/{operatorId}/availability")
    @ApiOperation(value = "Finds all available schedule for a operator in a Time Period",
            notes = "Provide an operator id to look all available schedule for a operator in a time period from Appointment Table" +
                    "Time Period is Optional, It shows appointments for next 24 hours if Time Period is not passed")
    public List<TimePeriod> getAvailability(@PathVariable("operatorId") long operatorId, @Valid @RequestBody TimePeriod timePeriod)
            throws UsernameNotFoundException {
        return operatorScheduleService.getAvailabilityInTime(operatorId, timePeriod);
    }
}
