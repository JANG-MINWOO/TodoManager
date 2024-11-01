package com.sparta.practice.domain.todo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.practice.domain.comment.entity.Comment;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.todo.dto.TodoRequestDto;
import com.sparta.practice.domain.todo.dto.TodoResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity //JPA 가 관리할 수 있는 Entity 클래스 지정
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Todo extends TimeStamped {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 설정
    private Long id;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference //역참조를 직렬화 할때 무시
    private Member member;

    @OneToMany(mappedBy="todo",cascade = CascadeType.ALL)
    @JsonManagedReference //직렬화 할때 사용
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "sharedTodos")
    @JsonBackReference //역참조를 직렬화 할때 무시
    private List<Member> sharedMembers = new ArrayList<>();

    public Todo(TodoRequestDto requestDto, Member member) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.member = member;
    }

    public void update(TodoRequestDto requestDto) {
        this.title=requestDto.getTitle();
        this.description=requestDto.getDescription();
    }
}
