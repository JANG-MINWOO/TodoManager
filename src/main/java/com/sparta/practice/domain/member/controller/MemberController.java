package com.sparta.practice.domain.member.controller;

import com.sparta.practice.domain.member.dto.MemberRequestDto;
import com.sparta.practice.domain.member.dto.MemberResponseDto;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    //회원가입
    @PostMapping("/resister")
    public ResponseEntity<Member> registerMember(@RequestBody @Valid MemberRequestDto requestDto) {
        Member newMember = memberService.registerMember(
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return ResponseEntity.ok(newMember);
    }

    //회원 정보 불러오기
    @GetMapping("/loadData")
    public ResponseEntity<Member> login(@RequestParam Long memberId, @RequestParam String password){
        Optional<Member> member = memberService.login(memberId, password);
        return member.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    //회원 정보 수정
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(
            @PathVariable Long memberId,
            @RequestBody @Valid MemberRequestDto requestDto
    ) {
        MemberResponseDto updatedMember = memberService.updateMember(memberId,requestDto);
        return ResponseEntity.ok(updatedMember);
    }

    //회원탈퇴
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }
}
