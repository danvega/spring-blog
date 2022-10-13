package dev.danvega.blog.repository;

import dev.danvega.blog.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authors;

    @Test
    void shouldReturnAllAuthors() {
        long count = StreamSupport.stream(authors.findAll().spliterator(), false).count();
        assertEquals(1, count);
    }

}
