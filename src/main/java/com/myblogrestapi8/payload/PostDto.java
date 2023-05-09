package com.myblogrestapi8.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotEmpty(message = "Is mandatory")
    @Size(min=2, message = "Post title should atleast 2 characters")
    private String title;



    @NotEmpty
    @Size(min=4, message = "Post description should atleast 4 characters")
    private String description;

    @NotEmpty
    @Size(min=5, message = "Post content should atleast 5 characters")
    private String content;
}
