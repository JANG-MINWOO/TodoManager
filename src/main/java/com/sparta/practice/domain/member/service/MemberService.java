package com.sparta.practice.domain.member.service;

import com.sparta.practice.domain.config.PasswordEncoder;
import com.sparta.practice.domain.jwt.JwtUtil;
import com.sparta.practice.domain.member.dto.MemberRequestDto;
import com.sparta.practice.domain.member.dto.MemberResponseDto;
import com.sparta.practice.domain.member.dto.SignupRequestDto;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.entity.MemberRoleEnum;
import com.sparta.practice.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId).orElse(null);
    }

    public Member registerMember(String username, String email, String password) {
        Member member = new Member(username, email, password);
        return memberRepository.save(member);
    }

    public Optional<Member> login(Long memberId, String password) {
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isPresent()&&member.get().getPassword().equals(password)) {
            return member;
        }
        return Optional.empty();
    }

    @Transactional
    public MemberResponseDto updateMember(Long memberId, MemberRequestDto requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                ()-> new EntityNotFoundException("회원을 찾을 수 없습니다.")
        );
        member.update(requestDto);
        return new MemberResponseDto(member);
    }

    @Transactional
    public void deleteMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException("회원을 찾을 수 없습니다.")
        );
        memberRepository.delete(member);
    }

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<Member> checkUsername = memberRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<Member> checkEmail = memberRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        MemberRoleEnum role = MemberRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = MemberRoleEnum.ADMIN;
        }

        // 사용자 등록
        Member member = new Member(username, password, email, role);
        memberRepository.save(member);
    }
}
