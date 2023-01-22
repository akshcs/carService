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
        List<TimePeriod> appointmentSchedules = getSortedSchedule(id, timePeriod);
        List<TimePeriod> mergeSortedSchedule = mergeSortedSchedule(appointmentSchedules);
        return getAvailabilityFromMergeSortedSchedule(mergeSortedSchedule, timePeriod);
    }

    public abstract List<Appointment> getAppointmentsFromIdAndTime(long id, TimePeriod timePeriod);

    private TimePeriod fillTimePeriodIfEmpty(TimePeriod timePeriod) throws InvalidTimeException {
        if(Objects.isNull(timePeriod.getStart())) {
            timePeriod.setStart(LocalDateTime.now());
            if(Objects.nonNull(timePeriod.getEnd())){
                throw new InvalidTimeException("Please provide Both Start and End Time");
            }
        } else{
            if(Objects.nonNull(timePeriod.getEnd()) && !timePeriod.getStart().isBefore(timePeriod.getEnd())){
                throw new InvalidTimeException("Start Time Should be Before End Time");
            }
        }
        if(Objects.isNull(timePeriod.getEnd())){
           timePeriod.setEnd(timePeriod.getStart().plusHours(24));
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

    private List<TimePeriod> getSortedSchedule(long operatorId, TimePeriod timePeriod) throws UsernameNotFoundException {
        List<Appointment> appointments = getAppointments(operatorId, timePeriod);
        List<TimePeriod> appointmentTimePeriods = new ArrayList<>();
        SortedSet<TimePeriod> sortedSchedule = new TreeSet<>();
        for(Appointment appointment: appointments){
            sortedSchedule.add(new TimePeriod(appointment.getStartTime(), appointment.getEndTime()));
        }
        for(TimePeriod tp: sortedSchedule) {
           appointmentTimePeriods.add(tp);
        }
        return appointmentTimePeriods;
    }

    private List<TimePeriod> mergeSortedSchedule(List<TimePeriod> sortedSchedule) throws UsernameNotFoundException {
        List<TimePeriod> mergedSortedSchedule = new ArrayList<>();
        if(sortedSchedule.size()>0) {
            LocalDateTime start = sortedSchedule.get(0).getStart();
            LocalDateTime end;
            for (int i = 1; i < sortedSchedule.size(); i++) {
                if (sortedSchedule.get(i).getStart().isAfter(sortedSchedule.get(i - 1).getEnd())) {
                    end = sortedSchedule.get(i - 1).getEnd();
                    mergedSortedSchedule.add(new TimePeriod(start, end));
                    start = sortedSchedule.get(i).getStart();
                }
            }
            end = sortedSchedule.get(sortedSchedule.size() - 1).getEnd();
            mergedSortedSchedule.add(new TimePeriod(start, end));
        }
        return mergedSortedSchedule;
    }

    private List<TimePeriod> getAvailabilityFromMergeSortedSchedule(List<TimePeriod> mergeSortedSchedule, TimePeriod timePeriod) {
        List<TimePeriod> sortedAvailableSchedule = new ArrayList<>();
        if(mergeSortedSchedule.size()>0) {
            return getAvailableTimePeriods(mergeSortedSchedule, timePeriod, sortedAvailableSchedule);
        } else {
            return Collections.singletonList(timePeriod);
        }
    }

    private List<TimePeriod> getAvailableTimePeriods(List<TimePeriod> mergeSortedSchedule, TimePeriod timePeriod, List<TimePeriod> sortedAvailableSchedule) {
        LocalDateTime start;
        int startingIndex = 0;
        if(!mergeSortedSchedule.get(0).getStart().isAfter(timePeriod.getStart())){
            start = mergeSortedSchedule.get(0).getEnd();
            startingIndex++;
        } else {
            start = timePeriod.getStart();
        }
        return getPendingTimePeriods(mergeSortedSchedule, timePeriod, sortedAvailableSchedule, start, startingIndex);
    }

    private List<TimePeriod> getPendingTimePeriods(List<TimePeriod> mergeSortedSchedule, TimePeriod timePeriod, List<TimePeriod> sortedAvailableSchedule, LocalDateTime start, int startingIndex) {
        LocalDateTime end;
        for(int i = startingIndex; i< mergeSortedSchedule.size(); i++){
            end = mergeSortedSchedule.get(i).getStart();
            sortedAvailableSchedule.add(new TimePeriod(start, end));
            start = mergeSortedSchedule.get(i).getEnd();
        }
        if(start.isBefore(timePeriod.getEnd())){
            end = timePeriod.getEnd();
            sortedAvailableSchedule.add(new TimePeriod(start, end));
        }
        return sortedAvailableSchedule;
    }

}
