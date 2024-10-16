package com.sparta.practice.domain.comment.service;

import com.sparta.practice.domain.comment.dto.CommentRequestDto;
import com.sparta.practice.domain.comment.dto.CommentResponseDto;
import com.sparta.practice.domain.comment.entity.Comment;
import com.sparta.practice.domain.comment.repository.CommentRepository;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.repository.MemberRepository;
import com.sparta.practice.domain.todo.entity.Todo;
import com.sparta.practice.domain.todo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        Todo todo = todoRepository.findById(requestDto.getTodoId()).orElseThrow(
                ()->new EntityNotFoundException("일정이 없습니다.")
        );
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(
                ()->new EntityNotFoundException("회원이 없습니다.")
        );

        Comment comment = new Comment(requestDto.getContent(), todo,member);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
