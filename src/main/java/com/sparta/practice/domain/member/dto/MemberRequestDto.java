package com.sparta.practice.domain.member.dto;

import com.sparta.practice.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String username;
    private String password;
    private String email;
}
