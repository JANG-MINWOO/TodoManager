package com.sparta.practice.domain.member.controller;

import com.sparta.practice.domain.member.dto.LoginRequestDto;
import com.sparta.practice.domain.member.dto.MemberRequestDto;
import com.sparta.practice.domain.member.dto.MemberResponseDto;
import com.sparta.practice.domain.member.dto.SignupRequestDto;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

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
        try {
            memberService.login(requestDto, res);

            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    //회원 정보 불러오기
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> loadMemberData(
            @PathVariable Long memberId,
            HttpServletRequest request
    ) {
        Member loginMember = (Member) request.getAttribute("member");
        MemberResponseDto responseDto = memberService.loadMemberData(memberId, loginMember);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    //회원 정보 수정
    @Transactional
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(
            @PathVariable Long memberId,
            @RequestBody @Valid MemberRequestDto requestDto,
            HttpServletRequest request
    ) {
        Member member = (Member) request.getAttribute("member");
        MemberResponseDto updatedMember = memberService.updateMember(memberId, requestDto, member);
        return ResponseEntity.ok(updatedMember);
    }

    //회원탈퇴
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

}
