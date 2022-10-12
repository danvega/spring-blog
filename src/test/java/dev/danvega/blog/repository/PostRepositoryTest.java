package dev.danvega.blog.repository;

import dev.danvega.blog.model.Author;
import dev.danvega.blog.model.Comment;
import dev.danvega.blog.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    PostRepository posts;

    @Autowired
    AuthorRepository authors;

    AggregateReference<Author,Integer> author;

    @BeforeEach
    void setUp() {
        author = AggregateReference.to(authors.save(new Author(null, "Dan", "Vega", "danvega@gmail.com", "dvega")).id());
    }

    @Test
    void shouldSaveValidPost() {
        Post post = new Post( "TEST", "...",author);
        assertNull(post.getId());
        Post reloaded = posts.save(post);
        assertNotNull(reloaded.getId());
    }

    @Test
    void shouldSaveValidPostWithoutAuthor() {
        Post post = new Post( "TEST", "...",null);
        assertNull(post.getId());
        Post reloaded = posts.save(post);
        assertNotNull(reloaded.getId());
        assertNull(reloaded.getAuthor());
    }

    @Test
    void shouldPostWithComments() {
        Post post = new Post( "TEST", "...",null);
        post.addComments(List.of(new Comment("Dan","test comment"),new Comment("Dan","test comment 2")));
        posts.save(post);

        Post p = posts.findById(post.getId()).orElse(null);
        assertNotNull(p);
        assertNotNull(p.getId());
        assertEquals(2,p.getComments().size());
        assertEquals("Dan",p.getComments().iterator().next().getName());
    }

    @Test
    void shouldPostWithNoCommentsReturns0AndNotNull() {
        Post post = new Post( "TEST", "...",null);
        posts.save(post);
        Post p = posts.findById(post.getId()).orElse(null);
        assertNotNull(p);
        assertEquals(0,p.getComments().size());
    }

}