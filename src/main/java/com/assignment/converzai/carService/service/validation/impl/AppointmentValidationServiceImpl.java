package com.assignment.converzai.carService.service.validation.impl;

import com.assignment.converzai.carService.dto.appointment.CreateAppointmentDTO;
import com.assignment.converzai.carService.dto.appointment.UpdateAppointmentDTO;
import com.assignment.converzai.carService.entity.appointment.Appointment;
import com.assignment.converzai.carService.exception.appointment.AppointmentConflictException;
import com.assignment.converzai.carService.exception.appointment.InvalidAppointmentException;
import com.assignment.converzai.carService.service.appointment.intf.AppointmentService;
import com.assignment.converzai.carService.service.validation.intf.AppointmentValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class AppointmentValidationServiceImpl implements AppointmentValidationService {
    @Autowired
    private AppointmentService appointmentService;

    @Override
    public void validateDateTime(UpdateAppointmentDTO updateAppointmentDTO) throws InvalidAppointmentException {
        validateDateTime(updateAppointmentDTO.getStartTime(), updateAppointmentDTO.getEndTime());
    }

    @Override
    public void validateDateTime(CreateAppointmentDTO createAppointmentDTO) throws InvalidAppointmentException{
        validateDateTime(createAppointmentDTO.getStartTime(), createAppointmentDTO.getEndTime());
    }

    private void validateDateTime(LocalDateTime startTime, LocalDateTime endTime) throws InvalidAppointmentException{
        if(!startTime.isBefore(endTime)){
            throw new InvalidAppointmentException("Start Time Should be before End Time");
        }
    }

    @Override
    public void isAppointmentInPast(Appointment appointment) throws InvalidAppointmentException{
        if(appointment.getStartTime().isBefore(LocalDateTime.now())){
            throw new InvalidAppointmentException("Start Time Should be in Future");
        }
    }

    @Override
    public Appointment validateAppointment(long appointmentId) throws InvalidAppointmentException {
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        if(Objects.isNull(appointment)){
            throw new InvalidAppointmentException("Invalid Appointment ID");
        }
        if(!appointment.getStatus().equals("active")){
            throw new InvalidAppointmentException("It is a cancelled Appointment");
        }
        return appointment;
    }

    @Override
    public void checkConflict(Appointment appointment) throws AppointmentConflictException {
        checkOperatorConflict(appointment);
        checkCustomerConflict(appointment);
    }

    private void checkOperatorConflict(Appointment appointment) throws AppointmentConflictException {
        List<Appointment> appointments = appointmentService.getAppointmentByOperatorAndTime(appointment.getOperator().getId(),
                appointment.getStartTime().plusSeconds(1), appointment.getEndTime().minusSeconds(1));
        if(appointments.size() == 1 && appointments.get(0).getId()!=appointment.getId()){
            throw new AppointmentConflictException("Operator : " + appointment.getOperator().getId() +  " has a conflict , Please Reschedule or Choose Another Operator");
        }
    }

    private void checkCustomerConflict(Appointment appointment) throws AppointmentConflictException {
        List<Appointment> appointments = appointmentService.getAppointmentByCustomerAndTime(appointment.getCustomer().getId(),
                appointment.getStartTime().plusSeconds(1), appointment.getEndTime().minusSeconds(1));
        if(appointments.size() == 1 && appointments.get(0).getId()!=appointment.getId()){
            throw new AppointmentConflictException("Customer : " + appointment.getCustomer().getId() + " has a conflict , Please Reschedule at other time");
        }
    }
}
