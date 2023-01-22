package com.assignment.converzai.carService.model;

import com.assignment.converzai.carService.entity.appointment.Appointment;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class AppointmentSerializer extends StdSerializer<Appointment> {

    public AppointmentSerializer() {
        this(null);
    }

    public AppointmentSerializer(Class<Appointment> t) {
        super(t);
    }

    @Override
    public void serialize(Appointment appointment, JsonGenerator gen, SerializerProvider provider) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        gen.writeStartObject();
        gen.writeNumberField("id", appointment.getId());
        gen.writeStringField("startTime", appointment.getStartTime().format(formatter));
        gen.writeStringField("endTime", appointment.getEndTime().format(formatter));
        gen.writeNumberField("customer", appointment.getCustomer().getId());
        gen.writeNumberField("operator", appointment.getOperator().getId());
        gen.writeStringField("url", "/ConverzAI/Assignment/appointments/" + appointment.getId() + "/get");
        gen.writeEndObject();
    }
}
