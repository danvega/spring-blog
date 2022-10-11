package dev.danvega.blog.repository;

import dev.danvega.blog.model.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PostRepository {

    private List<Post> posts;

    public PostRepository() {
        posts = List.of(new Post(1,"Hello, World!", "hello-world","Welcome to my blog!", LocalDateTime.now()));
    }

    public List<Post> findAll() {
        return posts;
    }

    public Post findById(Integer id) {
        return posts.stream().filter(post -> post.id() == id).findFirst().orElse(null);
    }

}
