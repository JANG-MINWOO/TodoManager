package com.sparta.practice.domain.member.repository;

import com.sparta.practice.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public Member findById(Long memberId) {
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbcTemplate.query(sql,rs ->{
            if(rs.next()){
                return Member.from(rs);
            } else{
                return null;
            }
        },memberId);
    }
}
