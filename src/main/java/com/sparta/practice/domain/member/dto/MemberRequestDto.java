package com.sparta.practice.domain.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class MemberRequestDto {
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

//    @Email(message = "이메일 형식을 확인해주세요.")
//    @Pattern(regexp = "[A-Za-z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
//    private String email;
}
