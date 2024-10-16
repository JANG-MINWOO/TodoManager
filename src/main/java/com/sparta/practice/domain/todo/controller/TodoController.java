package com.sparta.practice.domain.todo.controller;

import com.sparta.practice.domain.member.dto.MemberRequestDto;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.service.MemberService;
import com.sparta.practice.domain.todo.dto.TodoMemberDto;
import com.sparta.practice.domain.todo.dto.TodoRequestDto;
import com.sparta.practice.domain.todo.dto.TodoResponseDto;
import com.sparta.practice.domain.todo.entity.Todo;
import com.sparta.practice.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;
    private final MemberService memberService;

//    public TodoController(TodoService todoService){
//        this.todoService=todoService;
//    }
    //일정 생성
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto todoRequestDto,@RequestParam Long memberId){
        Member member = memberService.findById(memberId);
        if(member == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todoService.createTodo(todoRequestDto, member));
    }

    //전체 일정 조회
    @GetMapping
    public ResponseEntity<List<TodoMemberDto>> getTodoList(
            @RequestParam int page, @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.getTodoListWithPaging(page,size));
    }

    //선택 일정 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable Long todoId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.getTodo(todoId));
    }

    //선택 일정 수정
    @PutMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto requestDto){
        todoService.updateTodo(todoId,requestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    //선택 일정 삭제
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto requestDto
    ){
        todoService.deleteTodo(todoId,requestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
