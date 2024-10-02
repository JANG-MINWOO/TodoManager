package com.sparta.schedulemanagerapp.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String name;
    private String task;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
