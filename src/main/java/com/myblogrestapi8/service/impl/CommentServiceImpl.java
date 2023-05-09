package com.myblogrestapi8.service.impl;

import com.myblogrestapi8.entity.Comment;
import com.myblogrestapi8.entity.Post;
import com.myblogrestapi8.exception.BlogApiException;
import com.myblogrestapi8.exception.ResourceNotFoundException;
import com.myblogrestapi8.payload.CommentDto;
import com.myblogrestapi8.repository.CommentRepository;
import com.myblogrestapi8.repository.PostRepository;
import com.myblogrestapi8.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
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

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));

       List<Comment> comments = commentRepository.findByPostId(postId);
       return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
       Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));

       Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));

       if (comment.getPost().getId()!=post.getId()){
           throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");

       }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("postId","id",postId));

        Comment comment = commentRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("commentId","id",id));

        if (comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post not matching with comment");
        }

        comment.setId(id);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment newcomment = commentRepository.save(comment);

       return mapToDto(newcomment);
    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("postId","id",postId));

        Comment comment = commentRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("commentId","id",id));

        if (comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post not matching with comment");
        }

        commentRepository.deleteById(id);

    }

    private CommentDto mapToDto(Comment newComment){
        CommentDto commentDto = mapper.map(newComment, CommentDto.class);
        //CommentDto commentDto = new CommentDto();
        //commentDto.setId(newComment.getId());
        //commentDto.setName(newComment.getName());
        //commentDto.setEmail(newComment.getEmail());
        //commentDto.setBody(newComment.getBody());
        //commentDto.setPost(newComment.getPost());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
       Comment comment = mapper.map(commentDto, Comment.class);
        //Comment comment = new Comment();
        //comment.setName(commentDto.getName());
        //comment.setEmail(commentDto.getEmail());
        //comment.setBody(commentDto.getBody());
        return comment;
    }

}
