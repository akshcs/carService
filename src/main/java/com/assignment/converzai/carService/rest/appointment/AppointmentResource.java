package com.assignment.converzai.carService.rest.appointment;

import com.assignment.converzai.carService.dto.appointment.CreateAppointmentDTO;
import com.assignment.converzai.carService.dto.appointment.UpdateAppointmentDTO;
import com.assignment.converzai.carService.entity.appointment.Appointment;
import com.assignment.converzai.carService.exception.appointment.InvalidAppointmentException;
import com.assignment.converzai.carService.service.appointment.intf.AppointmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentResource {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{appointmentId}/get")
    @ApiOperation(value = "Finds Appointment by appointmentId",
            notes = "Provide an appointment id to look specific appointment from Appointment Table", response = Appointment.class)
    public Appointment getAppointment(@PathVariable("appointmentId") long appointmentId) throws InvalidAppointmentException{
        return appointmentService.getAppointment(appointmentId);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Finds All the Appointments",
            notes = "Looks up all the appointments from Appointment Table")
    public List<Appointment> getAllAppointment(){
        return appointmentService.getAllAppointment();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new Appointment",
            notes = "Provide appointment details to add a new appointment into Appointment Table." +
                    "Payload should be of type CreateAppointmentDTO. It checks that appointment should be scheduled in future. " +
                    "It should have no conflicts for involved customer and operator.", response = Appointment.class)
    public Appointment bookAppointment(@Valid @RequestBody CreateAppointmentDTO createAppointmentDTO) throws InvalidAppointmentException {
        return appointmentService.saveNewAppointment(createAppointmentDTO);
    }

    @PutMapping("/{appointmentId}/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update Appointment by appointmentId",
            notes = "Update an appointment's details. Use appointmentId to look specific appointment from Appointment Table and update it's values." +
                    "Payload should be of type UpdateAppointmentDTO. It checks that oldAppointment should be scheduled in future." +
                    "The new Appointment should be scheduled in future too. It should have no conflicts for involved customer and operator." , response = Appointment.class)
    public Appointment rescheduleAppointment(@PathVariable("appointmentId") long appointmentId, @Valid @RequestBody
    UpdateAppointmentDTO updateAppointmentDTO) throws Exception {
        return appointmentService.updateAppointment(appointmentId, updateAppointmentDTO);
    }

    @DeleteMapping("/{appointmentId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete Appointment by appointmentId",
            notes = "Use appointmentId to look specific appointment from Appointment Table and delete it")
    public Appointment cancelAppointment(@PathVariable("appointmentId") long appointmentId) throws InvalidAppointmentException {
        return appointmentService.deleteAppointment(appointmentId);
    }
}
