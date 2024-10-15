package com.sparta.practice.domain.todo.service;

import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.repository.MemberRepository;
import com.sparta.practice.domain.todo.dto.TodoMemberDto;
import com.sparta.practice.domain.todo.dto.TodoRequestDto;
import com.sparta.practice.domain.todo.dto.TodoResponseDto;
import com.sparta.practice.domain.todo.entity.Todo;
import com.sparta.practice.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {
        Todo todo = todoRepository.save(Todo.from(todoRequestDto));
        return todo.to();
    }

//    public List<TodoResponseDto> getTodoList() {
//        return todoRepository.findAll().stream().map(TodoResponseDto::new).toList();
//    }

    public List<TodoMemberDto> getTodoListWithPaging(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return todoRepository.findAll(pageable).stream()
                .map(todo -> new TodoMemberDto(todo.getId(),todo.getMemberId(),todo.getTitle(),todo.getDescription()))
                .collect(Collectors.toList());
    }

    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()->new NoSuchElementException("id를 찾을 수 없습니다."));
        return todo.to();
    }

    @Transactional
    public void updateTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = findTodo(todoId); //아래의 일정 존재유무 메서드
        Member member = memberRepository.findById(todo.getMemberId());

        if(!Objects.equals(member.getId(),requestDto.getMemberId())){
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }
        todo.update(requestDto);
    }

    @Transactional
    public void deleteTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = findTodo(todoId);
        Member member = memberRepository.findById(todo.getMemberId());

        if(!Objects.equals(member.getId(),requestDto.getMemberId())){
            throw new IllegalArgumentException("작성자만 삭제 가능합니다.");
        }
        todoRepository.delete(todo);
    }

    private Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(()-> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));
    }
}
