package com.sparta.practice.domain.comment.controller;

import com.sparta.practice.domain.comment.dto.CommentRequestDto;
import com.sparta.practice.domain.comment.dto.CommentResponseDto;
import com.sparta.practice.domain.comment.service.CommentService;
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

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto){
        CommentResponseDto commentDto = commentService.createComment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByTodoId(@PathVariable Long todoId){
        List<CommentResponseDto> comments = commentService.getCommentByTodoId(todoId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto){
        CommentResponseDto commentDto = commentService.updateComment(commentId,requestDto);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment (@PathVariable Long commentId, @RequestParam Long memberId){
        commentService.deleteComment(commentId,memberId);
        return ResponseEntity.noContent().build();
    }

}
