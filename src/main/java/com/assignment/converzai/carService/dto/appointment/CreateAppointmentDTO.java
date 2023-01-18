package com.assignment.converzai.carService.dto.appointment;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Component
public class CreateAppointmentDTO {

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public CreateAppointmentDTO() {
    }

    public CreateAppointmentDTO(LocalDateTime startTime, LocalDateTime endTime, long id_customer, long id_operator) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.id_customer = id_customer;
        this.id_operator = id_operator;
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public long getId_operator() {
        return id_operator;
    }

    public void setId_operator(long id_operator) {
        this.id_operator = id_operator;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    @Future
    @ApiModelProperty(notes = "Format is yyyy-MM-dd'T'HH:mm, Should be a future date", required = true)
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future
    @ApiModelProperty(notes = "Format is yyyy-MM-dd'T'HH:mm, Should be a future date")
    private LocalDateTime endTime;

    @NotNull
    @ApiModelProperty(required = true)
    private long id_customer;
    @NotNull
    @ApiModelProperty(required = true)
    private long id_operator;
}
