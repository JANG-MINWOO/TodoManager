package com.sparta.practice.domain.todo.dto;

import com.sparta.practice.domain.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {
    private Long id; //pk를 위해
    private Long memberId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TodoResponseDto(Todo todo){
        this.id=todo.getId();
        this.memberId = todo.getMember().getId();
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.createdAt = todo.getCreatedAt();
        this.updatedAt = todo.getUpdatedAt();
    }

}
