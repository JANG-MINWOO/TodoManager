package com.sparta.practice.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoResponseDto {
    private Long id; //pk를 위해
    private Long memberId;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
}
