package com.assignment.converzai.carService.service.appointment.impl;

import com.assignment.converzai.carService.dao.appointment.AppointmentRepository;
import com.assignment.converzai.carService.dto.appointment.CreateAppointmentDTO;
import com.assignment.converzai.carService.dto.appointment.UpdateAppointmentDTO;
import com.assignment.converzai.carService.entity.appointment.Appointment;
import com.assignment.converzai.carService.exception.appointment.AppointmentConflictException;
import com.assignment.converzai.carService.exception.appointment.InvalidAppointmentException;
import com.assignment.converzai.carService.service.appointment.intf.AppointmentService;
import com.assignment.converzai.carService.service.validation.intf.AppointmentValidationService;
import com.assignment.converzai.carService.service.user.intf.CustomerService;
import com.assignment.converzai.carService.service.user.intf.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentValidationService appointmentValidationService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    @Qualifier("customer")
    private CustomerService customerService;

    @Autowired
    @Qualifier("operator")
    private OperatorService operatorService;

    @Override
    public Appointment getAppointment(long appointmentId) throws InvalidAppointmentException {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new InvalidAppointmentException("AppointmentId with id : " + appointmentId +" not found!"));
    }
    @Override
    public List<Appointment> getAppointmentByOperatorAndTime(long operatorId, LocalDateTime startTime, LocalDateTime endTime) {
        return appointmentRepository.findByOperatorIdWithInPeriod(operatorId, startTime, endTime);
    }

    @Override
    public List<Appointment> getAppointmentByCustomerAndTime(long customerId, LocalDateTime startTime, LocalDateTime endTime) {
        return appointmentRepository.findByCustomerIdWithInPeriod(customerId, startTime, endTime);
    }

    @Override
    public List<Appointment> getAllAppointment() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment saveNewAppointment(CreateAppointmentDTO createAppointmentDTO) throws InvalidAppointmentException {
        Appointment appointment = createAppointmentFromCreateAppointmentDTO(createAppointmentDTO);
        try {
            appointmentValidationService.checkConflict(appointment);
        } catch (AppointmentConflictException ex){
            throw ex;
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(long appointmentId, UpdateAppointmentDTO updateAppointmentDTO) throws InvalidAppointmentException {
        Appointment oldAppointment = updateAppointmentFromUpdateAppointmentDTO(appointmentId, updateAppointmentDTO);
        try {
            appointmentValidationService.checkConflict(oldAppointment);
        } catch (AppointmentConflictException ex){
            throw ex;
        }
        return appointmentRepository.save(oldAppointment);
    }

    private CreateAppointmentDTO updateEndTimeIfEmpty(CreateAppointmentDTO createAppointmentDTO){
        if(Objects.isNull(createAppointmentDTO.getEndTime())){
            createAppointmentDTO.setEndTime(createAppointmentDTO.getStartTime().plusMinutes(60));
        }
        return createAppointmentDTO;
    }

    private Appointment createAppointmentFromCreateAppointmentDTO(CreateAppointmentDTO createAppointmentDTO) throws InvalidAppointmentException {
        createAppointmentDTO = updateEndTimeIfEmpty(createAppointmentDTO);
        appointmentValidationService.validateDateTime(createAppointmentDTO);
        return new Appointment(createAppointmentDTO.getStartTime(), createAppointmentDTO.getEndTime(),
                operatorService.getOperatorById(createAppointmentDTO.getId_operator()),  customerService.getCustomerById(createAppointmentDTO.getId_customer()));
    }

    private Appointment updateAppointmentFromUpdateAppointmentDTO(long appointmentId, UpdateAppointmentDTO updateAppointmentDTO) throws InvalidAppointmentException {
        Appointment oldAppointment = appointmentValidationService.validateAppointment(appointmentId);
        appointmentValidationService.isAppointmentInPast(oldAppointment);

        updateAppointmentDTO = fillAppointmentDTOFromAppointment(updateAppointmentDTO, oldAppointment);
        appointmentValidationService.validateDateTime(updateAppointmentDTO);

        oldAppointment.update(updateAppointmentDTO.getStartTime(), updateAppointmentDTO.getEndTime(),
                operatorService.getOperatorById(updateAppointmentDTO.getId_operator()),  customerService.getCustomerById(updateAppointmentDTO.getId_customer()));
        return oldAppointment;
    }

    @Override
    public Appointment deleteAppointment(long appointmentId) throws InvalidAppointmentException {
        Appointment appointment = appointmentValidationService.validateAppointment(appointmentId);
        appointmentValidationService.isAppointmentInPast(appointment);
        return appointmentRepository.save(appointment.cancel());
    }

    private UpdateAppointmentDTO fillAppointmentDTOFromAppointment(UpdateAppointmentDTO updateAppointmentDTO, Appointment appointment) {
        updateAppointmentDTO = fillTime(updateAppointmentDTO, appointment);
        return fillUserDetails(updateAppointmentDTO, appointment);
    }

    private UpdateAppointmentDTO fillTime(UpdateAppointmentDTO updateAppointmentDTO, Appointment appointment){
        if(Objects.isNull(updateAppointmentDTO.getEndTime())){
            updateAppointmentDTO.setEndTime(appointment.getEndTime());
        }
        if(Objects.isNull(updateAppointmentDTO.getStartTime())){
            updateAppointmentDTO.setStartTime(appointment.getStartTime());
        }
        return updateAppointmentDTO;
    }

    private UpdateAppointmentDTO fillUserDetails(UpdateAppointmentDTO updateAppointmentDTO, Appointment appointment){
        if(updateAppointmentDTO.getId_customer() == 0){
            updateAppointmentDTO.setId_customer(appointment.getCustomer().getId());
        }
        if(updateAppointmentDTO.getId_operator() == 0){
            updateAppointmentDTO.setId_operator(appointment.getOperator().getId());
        }
        return updateAppointmentDTO;
    }
}
