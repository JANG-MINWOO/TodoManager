package com.sparta.practice.domain.todo.repository;

import com.sparta.practice.domain.todo.dto.TodoMemberDto;
import com.sparta.practice.domain.todo.dto.TodoRequestDto;
import com.sparta.practice.domain.todo.dto.TodoResponseDto;
import com.sparta.practice.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepository {
    public final JdbcTemplate jdbcTemplate;

    public Todo save(Todo todo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO todo (member_id, title, password, description, created_At) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setLong(1,todo.getMemberId());
            ps.setString(2,todo.getTitle());
            ps.setString(3,todo.getPassword());
            ps.setString(4,todo.getDescription());
            ps.setString(5,todo.getCreatedAt());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        todo.setId(id);
        return todo;
    }

    public List<TodoResponseDto> findAll() {
        String sql = "SELECT * FROM todo " +
               "LEFT JOIN member m on todo.member_id = m.id" ;

        return jdbcTemplate.query(sql, (rs, rowNum)->{
            Long id =rs.getLong("id");
            Long memberId =rs.getLong("member_id");
            String title =rs.getString("title");
            String description =rs.getString("description");
            String createdAt =rs.getString("created_At");
            String updatedAt =rs.getString("updated_At");
            return new TodoResponseDto(
                    id,memberId,title,description,createdAt,updatedAt
            );
        });
    }

    public Todo findById(Long todoId) {
        String sql = "SELECT * FROM todo WHERE id = ?";

        return jdbcTemplate.query(sql, rs ->{
            if(rs.next()){
                return Todo.from(rs);
            }else{
                return null;
            }
        },todoId);
    }

    public void update(Long id, TodoRequestDto requestDto) {
        String sql = "UPDATE todo SET description = ?, member_id = ?, updated_at = ? WHERE id = ?";

        jdbcTemplate.update(sql,requestDto.getDescription(),requestDto.getMemberId(),requestDto.getUpdatedAt(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

    public List<TodoMemberDto> findAllWithPaging(int page, int size) {
        String sql = "SELECT * FROM todo " +
                "LEFT JOIN member m on todo.member_id = m.id " +
                "ORDER BY todo.id LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, (rs, rowNum)-> {
            Long id = rs.getLong("id");
            Long member_id = rs.getLong("member_id");
            String username =rs.getString("username");
            String title =rs.getString("title");
            String email = rs.getString("email");
            String description =rs.getString("description");
            String createdAt =rs.getString("created_At");
            String updatedAt =rs.getString("updated_At");
            return new TodoMemberDto(
                    id,member_id,username,title,email,description,createdAt,updatedAt
            );
        },size,page*size);
    }
}
