package dev.danvega.blog;

import dev.danvega.blog.model.Author;
import dev.danvega.blog.model.Comment;
import dev.danvega.blog.model.Post;
import dev.danvega.blog.repository.AuthorRepository;
import dev.danvega.blog.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class TestDataLoader implements CommandLineRunner {

    private final AuthorRepository authors;
    private final PostRepository posts;

    public TestDataLoader(AuthorRepository authors, PostRepository posts) {
        this.authors = authors;
        this.posts = posts;
    }

    @Override
    public void run(String... args) throws Exception {
        AggregateReference<Author,Integer> author = AggregateReference.to(authors.save(new Author(null, "Dan", "Vega", "danvega@gmail.com", "dvega")).id());
        Post post = new Post("Hello, World!","Welcome to my Spring Blog", author);
        post.addComment(new Comment( "Dan", "This is a comment"));
        post.addComment(new Comment( "John", "This is another comment"));
        posts.save(post);
    }
}
