package com.myblogrestapi8.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myblogrestapi8.entity.Post;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "name should be atleast 2 characters")
    private String name;
    @NotEmpty
    @Size(min = 6, message = "email should be atleast 6 characters")
    private String email;
    @NotEmpty
    @Size(min = 8, message = "body should be atleast 8 characters")
    private String body;

    @JsonIgnoreProperties("comments")
    private Post post;
}
