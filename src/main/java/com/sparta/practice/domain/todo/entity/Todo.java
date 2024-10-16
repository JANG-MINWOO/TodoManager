package com.sparta.practice.domain.todo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.todo.dto.TodoRequestDto;
import com.sparta.practice.domain.todo.dto.TodoResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity //JPA 가 관리할 수 있는 Entity 클래스 지정
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Todo extends TimeStamped {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 설정
    private Long id;

    private String title;
    private String password;
    private String description;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member member;


    public static Todo from(TodoRequestDto requestDto,Member member){
        Todo todo = new Todo();
        todo.init(requestDto,member);
        return todo;
    }

    private void init(TodoRequestDto requestDto,Member member){
        this.member = member;
        this.title=requestDto.getTitle();
        this.password=requestDto.getPassword();
        this.description=requestDto.getDescription();
        this.createdAt=requestDto.getCreatedAt();
        this.updatedAt=requestDto.getUpdatedAt();
    }

    public TodoResponseDto to() {
        return new TodoResponseDto(this);
    }

    public void update(TodoRequestDto requestDto) {
        this.title=requestDto.getTitle();
        this.description=requestDto.getDescription();
    }
}
