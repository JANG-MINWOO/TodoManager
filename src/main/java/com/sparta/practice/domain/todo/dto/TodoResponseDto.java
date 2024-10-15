package com.sparta.practice.domain.todo.dto;

import com.sparta.practice.domain.todo.entity.Todo;
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

    public TodoResponseDto(Todo todo){
        this.id = todo.getId();
        this.memberId=todo.getMemberId();
        this.title=todo.getTitle();
        this.description=todo.getDescription();
        this.createdAt=todo.getCreatedAt();
        this.updatedAt=todo.getUpdatedAt();
    }
}
