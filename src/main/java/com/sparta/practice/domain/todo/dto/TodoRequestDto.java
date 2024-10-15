package com.sparta.practice.domain.todo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoRequestDto {
    private Long memberId;
    private String title;
    private String password;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
