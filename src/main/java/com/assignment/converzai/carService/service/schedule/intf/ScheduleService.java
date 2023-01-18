package com.assignment.converzai.carService.service.schedule.intf;

import com.assignment.converzai.carService.entity.TimePeriod;
import com.assignment.converzai.carService.entity.appointment.Appointment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface ScheduleService {
    List<TimePeriod> getAppointmentsInTime(long id, TimePeriod timePeriod) throws UsernameNotFoundException;
    List<TimePeriod> getAvailabilityInTime(long id, TimePeriod timePeriod) throws UsernameNotFoundException;
    List<Appointment> getAppointmentsFromIdAndTime(long id, TimePeriod timePeriod);
}
