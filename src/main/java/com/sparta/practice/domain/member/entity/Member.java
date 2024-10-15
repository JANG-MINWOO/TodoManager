package com.sparta.practice.domain.member.entity;

import com.sparta.practice.domain.todo.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public static Member from(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.init(rs);
        return member;
    }

    private void init(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.username = rs.getString("username");
        this.email = rs.getString("email");
        Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
        this.createdAt = createdAtTimestamp != null ? createdAtTimestamp.toLocalDateTime() : null;
        Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
        this.updatedAt = updatedAtTimestamp != null ? updatedAtTimestamp.toLocalDateTime() : null;
    }
}
