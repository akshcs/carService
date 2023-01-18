package com.assignment.converzai.carService.service.appointment.intf;

import com.assignment.converzai.carService.dto.appointment.CreateAppointmentDTO;
import com.assignment.converzai.carService.dto.appointment.UpdateAppointmentDTO;
import com.assignment.converzai.carService.entity.appointment.Appointment;
import com.assignment.converzai.carService.exception.appointment.InvalidAppointmentException;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    public Appointment getAppointment(long appointmentID);

    List<Appointment> getAppointmentByOperatorAndTime(long operatorId, LocalDateTime startTime, LocalDateTime endTime);

    List<Appointment> getAppointmentByCustomerAndTime(long customerId, LocalDateTime startTime, LocalDateTime endTime);

    public List<Appointment> getAllAppointment();

    Appointment saveNewAppointment(CreateAppointmentDTO createAppointmentDTO) throws InvalidAppointmentException;

    Appointment updateAppointment(long appointmentId, UpdateAppointmentDTO updateAppointmentDTO) throws Exception;

    public Appointment deleteAppointment(long appointmentID);
}
