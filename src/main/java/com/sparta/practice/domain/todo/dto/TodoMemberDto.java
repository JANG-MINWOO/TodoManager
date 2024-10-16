package com.sparta.practice.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class TodoMemberDto {
    private Long id;
    private Long memberId;
    private String title;
    private String username;
    private String email;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TodoMemberDto(Long id, Long memberId, String title, String description, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.username = username;
        this.email = email;
    }
}
