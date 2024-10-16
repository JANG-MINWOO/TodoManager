package com.sparta.practice.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoMemberDto {
    private Long id;
    private Long memberId;
    private String title;
    private String username;
    private String email;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TodoMemberDto(Long id, Long memberId, String title, String description) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.description = description;
    }
}
