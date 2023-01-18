package com.assignment.converzai.carService.dao.appointment;

import com.assignment.converzai.carService.entity.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.customer.id = :customerId")
    List<Appointment> findByCustomerId(@Param("customerId") long customerId);

    @Query("select a from Appointment a where a.operator.id = :operatorId")
    List<Appointment> findByOperatorId(@Param("operatorId") long operatorId);

    @Query("select a from Appointment a where a.operator.id = :operatorId and a.status='active' and ((a.startTime >=:dayStart and  a.startTime <=:dayEnd) or (a.startTime <=:dayStart and a.endTime >=:dayStart))")
    List<Appointment> findByOperatorIdWithInPeriod(@Param("operatorId") long operatorId, @Param("dayStart") LocalDateTime startPeriod, @Param("dayEnd") LocalDateTime endPeriod);

    @Query("select a from Appointment a where a.customer.id = :customerId and a.status='active' and ((a.startTime >=:dayStart and  a.startTime <=:dayEnd) or (a.startTime <=:dayStart and a.endTime >=:dayStart))")
    List<Appointment> findByCustomerIdWithInPeriod(@Param("customerId") long customerId, @Param("dayStart") LocalDateTime startPeriod, @Param("dayEnd") LocalDateTime endPeriod);
}
