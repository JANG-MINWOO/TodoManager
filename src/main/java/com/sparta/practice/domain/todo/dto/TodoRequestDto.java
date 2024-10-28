package com.sparta.practice.domain.todo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoRequestDto {

    private Long memberId;

    @NotNull(message = "할일을 입력해주세요.")
    @Size(max=11,message = "10자 이하로 작성해주세요.")
    private String title;

    @NotEmpty(message = "할일을 입력해 주세요.")
    private String description;
}
