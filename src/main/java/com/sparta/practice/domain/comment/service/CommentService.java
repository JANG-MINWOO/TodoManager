package com.sparta.practice.domain.comment.service;

import com.sparta.practice.domain.comment.dto.CommentRequestDto;
import com.sparta.practice.domain.comment.dto.CommentResponseDto;
import com.sparta.practice.domain.comment.entity.Comment;
import com.sparta.practice.domain.comment.repository.CommentRepository;
import com.sparta.practice.domain.exception.UnauthorizedAccessException;
import com.sparta.practice.domain.member.entity.Member;
import com.sparta.practice.domain.member.repository.MemberRepository;
import com.sparta.practice.domain.todo.entity.Todo;
import com.sparta.practice.domain.todo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        Comment comment = new Comment(requestDto.getContent(), todo, member);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getCommentByTodoId(Long todoId) {
        List<Comment> comments = commentRepository.findByTodoId(todoId);

        if(comments.isEmpty()) {
            throw new EntityNotFoundException("이 일정에 대한 댓글이 존재하지 않습니다.");
        }

        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto)  {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new EntityNotFoundException("댓글이 존재하지 않습니다.")
        );

        if(!comment.getMember().getId().equals(requestDto.getMemberId())) {
            throw new UnauthorizedAccessException("작성자만 수정할 수 있습니다.");
        }

        comment.setContent(requestDto.getContent());
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("댓글이 존재하지 않습니다.")
        );
        if(!comment.getMember().getId().equals(memberId)) {
            throw new UnauthorizedAccessException("작성자만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }
}
