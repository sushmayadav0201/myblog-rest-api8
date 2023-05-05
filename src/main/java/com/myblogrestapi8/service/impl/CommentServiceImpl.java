package com.myblogrestapi8.service.impl;

import com.myblogrestapi8.entity.Comment;
import com.myblogrestapi8.entity.Post;
import com.myblogrestapi8.exception.ResourceNotFoundException;
import com.myblogrestapi8.payload.CommentDto;
import com.myblogrestapi8.repository.CommentRepository;
import com.myblogrestapi8.repository.PostRepository;
import com.myblogrestapi8.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto){

        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);

       Comment newComment = commentRepository.save(comment);

      CommentDto dto = mapToDto(newComment);
        return dto;
    }

    private CommentDto mapToDto(Comment newComment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(newComment.getId());
        commentDto.setName(newComment.getName());
        commentDto.setEmail(newComment.getEmail());
        commentDto.setBody(newComment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

}