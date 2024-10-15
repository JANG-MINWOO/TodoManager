package com.sparta.practice.domain.member.controller;

import com.sparta.practice.domain.member.dto.MemberRequestDto;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //회원가입
    @PostMapping("/resister")
    public ResponseEntity<Member> registerMember(@RequestBody MemberRequestDto requestDto) {
        Member newMember = memberService.registerMember(
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return ResponseEntity.ok(newMember);
    }

    //로그인
    @GetMapping("/login")
    public ResponseEntity<Member> login(@RequestParam String username, @RequestParam String password){
        Optional<Member> member = memberService.login(username, password);
        return member.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}
