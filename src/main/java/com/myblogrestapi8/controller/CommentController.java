package com.myblogrestapi8.controller;

import com.myblogrestapi8.payload.CommentDto;
import com.myblogrestapi8.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment
            (@PathVariable("postId") long PostId,
             @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(PostId,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
