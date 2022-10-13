package dev.danvega.blog.controller;

import dev.danvega.blog.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldFindAllPosts() {
        ResponseEntity<List<Post>> exchange = restTemplate.exchange("/api/posts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});
        assertNotNull(exchange);
        List<Post> posts = exchange.getBody();
        assertEquals(1,posts.size());
    }

}