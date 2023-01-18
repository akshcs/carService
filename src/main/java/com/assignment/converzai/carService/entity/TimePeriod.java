package com.assignment.converzai.carService.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@ApiModel(description = "This class is used to denote Time Period for availability and appointments in an interval")
public class TimePeriod implements Comparable<TimePeriod> {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @ApiModelProperty(notes = "DateFormat is yyyy-MM-dd'T'HH:mm, It denotes start time of a Time Period")
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @ApiModelProperty(notes = "DateFormat is yyyy-MM-dd'T'HH:mm, It denotes end time of a Time Period")
    private LocalDateTime end;

    public TimePeriod() {
    }

    public TimePeriod(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public int compareTo(TimePeriod o) {
        return this.getStart().compareTo(o.getStart());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimePeriod peroid = (TimePeriod) o;
        return this.start.equals(peroid.getStart()) &&

                this.end.equals(peroid.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "TimePeroid{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
