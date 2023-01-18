package com.assignment.converzai.carService.entity.appointment;

import com.assignment.converzai.carService.entity.common.BaseEntity;
import com.assignment.converzai.carService.entity.user.Customer;
import com.assignment.converzai.carService.entity.user.Operator;
import com.assignment.converzai.carService.model.AppointmentSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@JsonSerialize(using = AppointmentSerializer.class)
@ApiModel(description = "It contains all Appointments, If status becomes cancelled appointment is cancelled")
public class Appointment extends BaseEntity implements Comparable<Appointment>{

    public Appointment(LocalDateTime startTime, LocalDateTime endTime, Operator operator, Customer customer) {
        update(startTime, endTime, operator, customer);
        this.status = "active";
    }

    public void update(LocalDateTime startTime, LocalDateTime endTime, Operator operator, Customer customer) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.customer = customer;
        this.operator = operator;
    }

    public Appointment cancel() {
        this.status = "cancelled";
        this.canceledAt = LocalDateTime.now();
        return this;
    }

    public Appointment() {
    }

    public LocalDateTime getStartTime() {
        return startTime;
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

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(LocalDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "startTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @ApiModelProperty(notes = "DateFormat is yyyy-MM-dd'T'HH:mm, It denotes start time of an appointment")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "endTime")
    @ApiModelProperty(notes = "DateFormat is yyyy-MM-dd'T'HH:mm, It denotes end time of an appointment")
    private LocalDateTime endTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "canceled_at")
    @ApiModelProperty(notes = "DateFormat is yyyy-MM-dd'T'HH:mm, It gets added when an appointment is cancelled")
    private LocalDateTime canceledAt;
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "id_operator")
    private Operator operator;

    @Column(name = "status")
    private String status;

    @Override
    public int compareTo(Appointment o) {
        return this.getStartTime().compareTo(o.getStartTime());
    }
}
