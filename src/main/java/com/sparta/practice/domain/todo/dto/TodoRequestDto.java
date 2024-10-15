package com.sparta.practice.domain.todo.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private Long memberId;
    private String title;
    private String password;
    private String description;
    private String createdAt;
    private String updatedAt;
}
