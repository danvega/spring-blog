package dev.danvega.blog;

import dev.danvega.blog.model.Author;
import dev.danvega.blog.model.Comment;
import dev.danvega.blog.model.Post;
import dev.danvega.blog.repository.AuthorRepository;
import dev.danvega.blog.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Profile({"dev","prod"})
	@Bean
	CommandLineRunner run(PostRepository postRepository, AuthorRepository authorRepository) {
		return args -> {
			AggregateReference<Author,Integer> author = AggregateReference.to(authorRepository.save(new Author(null, "Dan", "Vega", "danvega@gmail.com", "dvega")).id());

			Post post = new Post( "Dan's First Post", "This is Dan's First Post",author);
			post.addComment(new Comment( "Dan", "This is a comment"));
			post.addComment(new Comment( "John", "This is another comment"));
			postRepository.save(post);
		};
	}
}
