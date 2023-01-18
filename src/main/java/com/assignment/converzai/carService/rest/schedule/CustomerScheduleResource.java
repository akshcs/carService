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
@RequestMapping("/customerSchedule")
public class CustomerScheduleResource {
    @Autowired
    @Qualifier("CustomerScheduleService")
    private ScheduleService customerScheduleService;

    @GetMapping("/{customerId}/appointments")
    @ApiOperation(value = "Finds all active Appointments for a customer in a Time Period",
            notes = "Provide an customer id to look all appointment for a customer in a time period from Appointment Table" +
                    "Time Period is Optional, It shows appointments for next 24 hours if Time Period is not passed")
    public List<TimePeriod> getAppointments(@PathVariable("customerId") long customerId, @Valid @RequestBody TimePeriod timePeriod)
            throws UsernameNotFoundException {
        return customerScheduleService.getAppointmentsInTime(customerId, timePeriod);
    }

    @GetMapping("/{customerId}/availability")
    @ApiOperation(value = "Finds all available schedule for a customer in a Time Period",
            notes = "Provide an customer id to look all available schedule for a customer in a time period from Appointment Table" +
                    "Time Period is Optional, It shows appointments for next 24 hours if Time Period is not passed")
    public List<TimePeriod> getAvailability(@PathVariable("customerId") long customerId, @Valid @RequestBody TimePeriod timePeriod)
            throws UsernameNotFoundException {
        return customerScheduleService.getAvailabilityInTime(customerId, timePeriod);
    }
}
