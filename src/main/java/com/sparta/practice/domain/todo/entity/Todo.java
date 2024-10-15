package com.sparta.practice.domain.todo.entity;

import com.sparta.practice.domain.todo.dto.TodoRequestDto;
import com.sparta.practice.domain.todo.dto.TodoResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Entity //JPA 가 관리할 수 있는 Entity 클래스 지정
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Todo {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 설정
    private Long id;

    private Long memberId;
    private String title;
    private String password;
    private String description;
    private String createdAt;
    private String updatedAt;

    public static Todo from(TodoRequestDto requestDto){
        Todo todo = new Todo();
        todo.init(requestDto);
        return todo;
    }

    public static Todo from(ResultSet rs) throws SQLException {
        Todo todo = new Todo();
        todo.init(rs);
        return todo;
    }

    private void init(ResultSet rs) throws SQLException {
        this.id=rs.getLong("id");
        this.memberId=rs.getLong("member_id");
        this.title=rs.getString("title");
        this.password=rs.getString("password");
        this.description=rs.getString("description");
        this.createdAt=rs.getString("created_at");
        this.updatedAt=rs.getString("updated_at");
    }


    private void init(TodoRequestDto requestDto){
        this.memberId=requestDto.getMemberId();
        this.title=requestDto.getTitle();
        this.password=requestDto.getPassword();
        this.description=requestDto.getDescription();
        this.createdAt=requestDto.getCreatedAt();
        this.updatedAt=requestDto.getUpdatedAt();
    }

    public TodoResponseDto to() {
        return new TodoResponseDto(
                this.id,
                this.memberId,
                this.title,
                this.description,
                this.createdAt,
                this.updatedAt
        );
    }

    public void update(TodoRequestDto requestDto) {
        this.title=requestDto.getTitle();
        this.description=requestDto.getDescription();
        this.updatedAt=requestDto.getUpdatedAt();
        this.memberId=requestDto.getMemberId();
    }
}
