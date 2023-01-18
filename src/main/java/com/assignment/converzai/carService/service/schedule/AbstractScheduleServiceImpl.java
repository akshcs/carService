package com.assignment.converzai.carService.service.schedule;

import com.assignment.converzai.carService.entity.TimePeriod;
import com.assignment.converzai.carService.entity.appointment.Appointment;
import com.assignment.converzai.carService.exception.InvalidTimeException;
import com.assignment.converzai.carService.service.appointment.intf.AppointmentService;
import com.assignment.converzai.carService.service.schedule.intf.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

public abstract class AbstractScheduleServiceImpl implements ScheduleService {

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public List<TimePeriod> getAppointmentsInTime(long id, TimePeriod timePeriod) throws UsernameNotFoundException {
        List<TimePeriod> appointmentTimePeriods = new ArrayList<>();
        appointmentTimePeriods.addAll(getSortedSchedule(id, timePeriod));
        return appointmentTimePeriods;
    }

    @Override
    public List<TimePeriod> getAvailabilityInTime(long id, TimePeriod timePeriod) throws UsernameNotFoundException {
        SortedSet<TimePeriod> appointmentSchedules = getSortedSchedule(id, timePeriod);
        SortedSet<TimePeriod> mergeSortedSchedule = mergeSortedSchedule(appointmentSchedules);
        List<TimePeriod> availabilityTimePeriods = new ArrayList<>();
        availabilityTimePeriods.addAll(getAvailabilityFromMergeSortedSchedule(mergeSortedSchedule, timePeriod));
        return availabilityTimePeriods;
    }

    public abstract List<Appointment> getAppointmentsFromIdAndTime(long id, TimePeriod timePeriod);

    private TimePeriod fillTimePeriodIfEmpty(TimePeriod timePeriod) throws InvalidTimeException {
        if(Objects.isNull(timePeriod.getStart()) && Objects.isNull(timePeriod.getEnd())){
            timePeriod.setStart(LocalDateTime.now());
            timePeriod.setEnd(LocalDateTime.now().plusHours(24));
        }
        else if(Objects.nonNull(timePeriod.getStart()) && Objects.isNull(timePeriod.getEnd())){
            timePeriod.setEnd(timePeriod.getStart().plusHours(24));
        } else if(Objects.isNull(timePeriod.getStart()) && Objects.nonNull(timePeriod.getEnd())){
            throw new InvalidTimeException("Please provide Both Start and End Time");
        } else {
            if(!timePeriod.getStart().isBefore(timePeriod.getEnd())){
                throw new InvalidTimeException("Start Time Should be Before End Time");
            }
        }
        return timePeriod;
    }

    private List<Appointment> getAppointments(long id, TimePeriod timePeriod) throws UsernameNotFoundException {
        try {
            timePeriod = fillTimePeriodIfEmpty(timePeriod);
        } catch (InvalidTimeException ex){
            throw ex;
        }
        return getAppointmentsFromIdAndTime(id, timePeriod);
    }

    private SortedSet<TimePeriod> getSortedSchedule(long operatorId, TimePeriod timePeriod) throws UsernameNotFoundException {
        List<Appointment> appointments = getAppointments(operatorId, timePeriod);
        SortedSet<TimePeriod> sortedSchedule = new TreeSet<>();
        for(Appointment appointment: appointments){
            sortedSchedule.add(new TimePeriod(appointment.getStartTime(), appointment.getEndTime()));
        }
        return sortedSchedule;
    }

    private SortedSet<TimePeriod> mergeSortedSchedule(SortedSet<TimePeriod> sortedSchedule) throws UsernameNotFoundException {
        SortedSet<TimePeriod> mergedSortedSchedule = new TreeSet<>();
        LocalDateTime start = null;
        LocalDateTime end = null;
        for(TimePeriod timePeriod: sortedSchedule) {
            if(Objects.nonNull(end) && !(timePeriod.getStart().equals(end))){
                mergedSortedSchedule.add(new TimePeriod(start, end));
                start = timePeriod.getStart();
            } else if(Objects.isNull(end)){
                start = timePeriod.getStart();
            }
            end = timePeriod.getEnd();
        }
        mergedSortedSchedule.add(new TimePeriod(start, end));
        return mergedSortedSchedule;
    }

    private SortedSet<TimePeriod> getAvailabilityFromMergeSortedSchedule(SortedSet<TimePeriod> mergeSortedSchedule, TimePeriod timePeriod) {
        SortedSet<TimePeriod> sortedAvailableSchedule = new TreeSet<>();
        LocalDateTime start = null;
        LocalDateTime end = null;
        for(TimePeriod tp: mergeSortedSchedule) {
            if(!tp.getStart().isAfter(timePeriod.getStart())  && !tp.getEnd().isBefore(timePeriod.getEnd())){
                return sortedAvailableSchedule;
            } else if(!tp.getStart().isAfter(timePeriod.getStart())  && tp.getEnd().isBefore(timePeriod.getEnd())) {
                start = tp.getEnd();
            } else if(tp.getStart().isAfter(timePeriod.getStart())  && !tp.getEnd().isBefore(timePeriod.getEnd())){
                if(Objects.isNull(start)){
                    start = timePeriod.getStart();
                }
                end = tp.getStart();
                sortedAvailableSchedule.add(new TimePeriod(start, end));
                return sortedAvailableSchedule;
            } else {
                if(Objects.isNull(start)){
                    start = timePeriod.getStart();
                }
                end = tp.getStart();
                sortedAvailableSchedule.add(new TimePeriod(start, end));
                start = tp.getEnd();
            }
        }
        if(!end.isAfter(timePeriod.getEnd())){
            end = timePeriod.getEnd();
            sortedAvailableSchedule.add(new TimePeriod(start, end));
            return sortedAvailableSchedule;
        }
        return sortedAvailableSchedule;
    }

}
