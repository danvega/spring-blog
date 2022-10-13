package dev.danvega.blog.repository;

import dev.danvega.blog.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author,Integer> {
    Author findByUsername(String username);
}
