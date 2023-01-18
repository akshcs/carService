package com.assignment.converzai.carService.entity.user;

import com.assignment.converzai.carService.dto.user.CreateUserDTO;
import com.assignment.converzai.carService.entity.appointment.Appointment;
import com.assignment.converzai.carService.entity.role.Role;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id_customer")
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointments;

    public Customer() {
    }

    public Customer(CreateUserDTO createUserDTO, Collection<Role> roles) {
        super(createUserDTO, roles);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return customer.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointments);
    }
}
