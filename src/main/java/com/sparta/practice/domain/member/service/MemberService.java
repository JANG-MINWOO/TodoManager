package com.sparta.practice.domain.member.service;

import com.sparta.practice.domain.exception.UnauthorizedAccessException;
import com.sparta.practice.domain.jwt.JwtUtil;
import com.sparta.practice.domain.member.dto.LoginRequestDto;
import com.sparta.practice.domain.member.dto.MemberRequestDto;
import com.sparta.practice.domain.member.dto.MemberResponseDto;
import com.sparta.practice.domain.member.dto.SignupRequestDto;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.entity.MemberRoleEnum;
import com.sparta.practice.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN 가입시, ADMIN 가입 유무를 TRUE 로 체크하고 아래의 토큰을 입력하여야 가입가능
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        // 회원 중복 확인
        Optional<Member> checkUsername = memberRepository.findByUsername(username);
        if (checkUsername.isPresent()) throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        // email 중복확인
        String email = requestDto.getEmail();
        Optional<Member> checkEmail = memberRepository.findByEmail(email);
        if (checkEmail.isPresent()) throw new IllegalArgumentException("중복된 Email 입니다.");
        // 사용자 ROLE 확인
        MemberRoleEnum role = MemberRoleEnum.USER;
        if (requestDto.isAdmin()) {
            System.out.println("Admin Token from request: " + requestDto.getAdminToken());
            System.out.println("Expected Admin Token: " + ADMIN_TOKEN);
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            role = MemberRoleEnum.ADMIN;
        }
        // 사용자 등록
        Member member = new Member(username, password, email, role);
        memberRepository.save(member);
    }

    public void login(LoginRequestDto requestDto,HttpServletResponse res) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        if(!passwordEncoder.matches(password, member.getPassword())) throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        // JWT 생성 + 쿠키 저장 + Response 객체에 추가
        String token = jwtUtil.createToken(member.getEmail(), member.getRole());
        jwtUtil.addJwtToCookie(token,res);
    }

    public MemberResponseDto loadMemberData(Long memberId, Member loginMember) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("검색한 회원을 찾을 수 없습니다."));
        return new MemberResponseDto(member);
    }

    @Transactional
    public void updateMember(Long memberId, MemberRequestDto requestDto, Member member) {
        Member updatedMember = memberRepository.findById(memberId).orElseThrow(()-> new EntityNotFoundException("회원을 찾을 수 없습니다."));
        if(!member.getId().equals(updatedMember.getId())) throw new UnauthorizedAccessException("수정할 권한이 없습니다.");

        String password = passwordEncoder.encode(requestDto.getPassword());
        updatedMember.update(password);
    }

    @Transactional
    public void deleteMember(Long memberId, Member member){
        Member deleteMember = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
        if(!member.getId().equals(deleteMember.getId())) throw new UnauthorizedAccessException("삭제할 권한이 없습니다.");

        memberRepository.delete(member);
    }
}
