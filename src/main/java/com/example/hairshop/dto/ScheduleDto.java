package com.example.hairshop.dto;

import com.example.hairshop.domain.Schedule;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleDto {
    private Long id;
    private LocalDate date;
    private boolean[] time;

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.date = schedule.getDate();
        this.time = schedule.getTime();
    }
}
