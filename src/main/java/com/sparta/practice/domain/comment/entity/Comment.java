package com.sparta.practice.domain.comment.entity;

import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.todo.entity.TimeStamped;
import com.sparta.practice.domain.todo.entity.Todo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name="todo_id",nullable = false)
    private Todo todo;

    @ManyToOne
    @JoinColumn(name="member_id",nullable = false)
    private Member member;

    public Comment(String content, Todo todo, Member member){
        this.content = content;
        this.todo=todo;
        this.member=member;
    }
}
