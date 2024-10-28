package com.sparta.practice.domain.member.controller;

import com.sparta.practice.domain.jwt.JwtUtil;
import com.sparta.practice.domain.member.dto.LoginRequestDto;
import com.sparta.practice.domain.member.dto.MemberRequestDto;
import com.sparta.practice.domain.member.dto.MemberResponseDto;
import com.sparta.practice.domain.member.dto.SignupRequestDto;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

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
    public ResponseEntity<Member> loadMemberData(@RequestParam Long memberId, @RequestParam String password){
        Optional<Member> member = memberService.loadMemberData(memberId, password);
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

    //도전과제 회원가입 기능
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        memberService.signup(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("회원가입이 완료되었습니다.");
    }

    //도전 로그인 기능
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse res) {
        try{
            memberService.login(requestDto, res);

            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: "+e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }
}
