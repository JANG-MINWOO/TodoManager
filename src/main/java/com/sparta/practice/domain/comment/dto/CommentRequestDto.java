package com.sparta.practice.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;
    private Long todoId;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
