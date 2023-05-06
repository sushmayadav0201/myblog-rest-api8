package com.myblogrestapi8.service;

import com.myblogrestapi8.entity.Comment;
import com.myblogrestapi8.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(long postId, long commentId);
}
