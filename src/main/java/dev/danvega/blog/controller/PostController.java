package dev.danvega.blog.controller;

import dev.danvega.blog.model.Post;
import dev.danvega.blog.repository.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository posts;

    public PostController(PostRepository posts) {
        this.posts = posts;
    }

    @GetMapping
    public List<Post> findAll() {
        return posts.findAll();
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Integer id) {
        return posts.findById(id);
    }
}
