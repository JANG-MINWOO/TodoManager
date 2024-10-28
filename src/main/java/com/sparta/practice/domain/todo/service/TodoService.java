package com.sparta.practice.domain.todo.service;

import com.sparta.practice.domain.exception.UnauthorizedAccessException;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.entity.MemberRoleEnum;
import com.sparta.practice.domain.todo.dto.TodoMemberDto;
import com.sparta.practice.domain.todo.dto.TodoRequestDto;
import com.sparta.practice.domain.todo.dto.TodoResponseDto;
import com.sparta.practice.domain.todo.entity.Todo;
import com.sparta.practice.domain.todo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto, Member member) {
        Todo todo = new Todo(requestDto, member);
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }

    public List<TodoMemberDto> getTodoListWithPaging(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return todoRepository.findAll(pageable).stream()
                .map(todo -> new TodoMemberDto(
                        todo.getId(),
                        todo.getMember().getId(),
                        todo.getTitle(),
                        todo.getDescription(),
                        todo.getMember().getUsername(),
                        todo.getMember().getEmail(),
                        todo.getCreatedAt(),
                        todo.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()->new EntityNotFoundException("id를 찾을 수 없습니다."));
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, Member member) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        //관리자 권한이 있으면 수정가능하게 추가
        if(!Objects.equals(member.getId(),requestDto.getMemberId())
        && member.getRole() != MemberRoleEnum.ADMIN) throw new UnauthorizedAccessException("작성자 또는 관리자만 수정 가능합니다.");

        todo.update(requestDto);
        todoRepository.saveAndFlush(todo);
        return new TodoResponseDto(todo);
    }

    @Transactional
    public void deleteTodo(Long todoId, TodoRequestDto requestDto, Member member) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        if(!Objects.equals(member.getId(),requestDto.getMemberId())
        && member.getRole() != MemberRoleEnum.ADMIN){
            throw new UnauthorizedAccessException("작성자 또는 관리자만 삭제 가능합니다.");
        }
        todoRepository.delete(todo);
    }
}
