package com.sparta.practice.domain.comment.controller;

import com.sparta.practice.domain.comment.dto.CommentRequestDto;
import com.sparta.practice.domain.comment.dto.CommentResponseDto;
import com.sparta.practice.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo/comments")
public class CommentController {
    private final CommentService commentService;

    //댓글생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody @Valid CommentRequestDto requestDto){
        CommentResponseDto commentDto = commentService.createComment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    //댓글 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByTodoId(@PathVariable Long todoId){
        List<CommentResponseDto> comments = commentService.getCommentByTodoId(todoId);
        return ResponseEntity.ok(comments);
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto requestDto){
        CommentResponseDto commentDto = commentService.updateComment(commentId,requestDto);
        return ResponseEntity.ok(commentDto);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}/{memberId}")
    public ResponseEntity<Void> deleteComment (@PathVariable Long commentId, @PathVariable Long memberId){
        commentService.deleteComment(commentId,memberId);
        return ResponseEntity.noContent().build();
    }

}
