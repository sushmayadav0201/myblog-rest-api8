package com.myblogrestapi8.controller;

import com.myblogrestapi8.payload.PostDto;
import com.myblogrestapi8.payload.PostResponse;
import com.myblogrestapi8.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
   //http://localhost:8080/api/posts
    @PostMapping
   public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
       PostDto dto = postService.createPost(postDto);
       return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = "0" , required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = "id", required = false)String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
                                     ){
       return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
    //http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostById(id);
        return ResponseEntity.ok(dto);
    }
    //http://localhost:8080/api/posts/{id}

    @PutMapping("/{id}")

    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id){
       PostDto dto = postService.updatePost(postDto, id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{id}
    @DeleteMapping("/{id}")

    public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted!!", HttpStatus.OK);

    }
}
