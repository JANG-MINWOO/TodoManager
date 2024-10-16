package com.sparta.practice.domain.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    @NotEmpty(message = "댓글 내용은 필수입니다.")
    @Size(max=100,message = "댓글은 100자 이하로 작성해주세요")
    private String content;

    private Long todoId;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
