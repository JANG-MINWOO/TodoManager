package com.sparta.practice.domain.member.dto;

import com.sparta.practice.domain.member.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class MemberResponseDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
    }
}
