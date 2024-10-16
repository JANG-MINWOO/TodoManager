package com.sparta.practice.domain.member.service;

import com.sparta.practice.domain.member.dto.MemberRequestDto;
import com.sparta.practice.domain.member.dto.MemberResponseDto;
import com.sparta.practice.domain.member.entity.Member;
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
}
