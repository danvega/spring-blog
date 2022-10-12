package dev.danvega.blog.model.dto;

import dev.danvega.blog.model.Author;
import dev.danvega.blog.model.Post;

public record PostDetails(Post post, Author author) { }
