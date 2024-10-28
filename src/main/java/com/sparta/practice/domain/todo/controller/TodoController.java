package com.sparta.practice.domain.todo.controller;

import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.todo.dto.TodoMemberDto;
import com.sparta.practice.domain.todo.dto.TodoRequestDto;
import com.sparta.practice.domain.todo.dto.TodoResponseDto;
import com.sparta.practice.domain.todo.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    //일정 생성
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(
            @RequestBody @Valid TodoRequestDto todoRequestDto,
            HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        TodoResponseDto responseDto = todoService.createTodo(todoRequestDto, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //전체 일정 조회
    @GetMapping
    public ResponseEntity<List<TodoMemberDto>> getTodoList(
            @RequestParam int page, @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoListWithPaging(page, size));
    }

    //선택 일정 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable Long todoId) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodo(todoId));
    }

    //선택 일정 수정
    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable Long todoId,
            @RequestBody @Valid TodoRequestDto requestDto,
            HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        TodoResponseDto responseDto = todoService.updateTodo(todoId, requestDto, member);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    //선택 일정 삭제
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto requestDto,
            HttpServletRequest request
    ) {
        Member member = (Member) request.getAttribute("member");
        todoService.deleteTodo(todoId, requestDto, member);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
