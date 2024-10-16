package com.sparta.practice.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.practice.domain.member.dto.MemberRequestDto;
import com.sparta.practice.domain.todo.entity.TimeStamped;
import com.sparta.practice.domain.todo.entity.Todo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference //직렬화 할때 사용
    private List<Todo> todos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "member_todo",
            joinColumns = @JoinColumn(name="member_id"),
            inverseJoinColumns = @JoinColumn(name = "todo_id")
    )
    @JsonManagedReference //직렬화 할때 사용
    private List<Todo> sharedTodos = new ArrayList<>();

    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Member(String username, String password, String email, MemberRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void update(MemberRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
    }
}
