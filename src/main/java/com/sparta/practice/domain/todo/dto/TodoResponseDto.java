package com.sparta.practice.domain.todo.dto;

import com.sparta.practice.domain.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoResponseDto {
    private Long id; //pk를 위해
    private Long memberId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
