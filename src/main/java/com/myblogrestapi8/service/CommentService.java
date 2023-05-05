package com.myblogrestapi8.service;

import com.myblogrestapi8.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
