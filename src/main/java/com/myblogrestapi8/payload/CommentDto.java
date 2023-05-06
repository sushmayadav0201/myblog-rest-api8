package com.myblogrestapi8.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myblogrestapi8.entity.Post;
import lombok.Data;
@Data
public class CommentDto {
    private long id;
    private String name;
    private String email;
    private String body;

    @JsonIgnoreProperties("comments")
    private Post post;
}
