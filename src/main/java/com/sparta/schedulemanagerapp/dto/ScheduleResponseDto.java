package com.sparta.schedulemanagerapp.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponseDto {
    private Long id;
    private String name;
    private String task;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public ScheduleResponseDto(Long id, String name, String task, LocalDateTime createdDate, LocalDateTime updatedDate) {
    }
}