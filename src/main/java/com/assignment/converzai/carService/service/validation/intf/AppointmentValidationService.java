package com.assignment.converzai.carService.service.validation.intf;

import com.assignment.converzai.carService.dto.appointment.CreateAppointmentDTO;
import com.assignment.converzai.carService.dto.appointment.UpdateAppointmentDTO;
import com.assignment.converzai.carService.entity.appointment.Appointment;
import com.assignment.converzai.carService.exception.appointment.AppointmentConflictException;
import com.assignment.converzai.carService.exception.appointment.InvalidAppointmentException;

import java.time.DateTimeException;

public interface AppointmentValidationService {

    void validateDateTime(UpdateAppointmentDTO updateAppointmentDTO) throws InvalidAppointmentException;

    void validateDateTime(CreateAppointmentDTO createAppointmentDTO) throws InvalidAppointmentException;

    void isAppointmentInPast(Appointment appointment) throws InvalidAppointmentException;

    Appointment validateAppointment(long appointmentID) throws InvalidAppointmentException;

    void checkConflict(Appointment appointment) throws AppointmentConflictException;
}
