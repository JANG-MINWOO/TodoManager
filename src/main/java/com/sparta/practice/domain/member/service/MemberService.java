package com.sparta.practice.domain.member.service;

import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Member> login(String username, String password) {
        Optional<Member> member = memberRepository.findByUsername(username);

        if(member.isPresent()&&member.get().getPassword().equals(password)) {
            return member;
        }
        return Optional.empty();
    }
}
