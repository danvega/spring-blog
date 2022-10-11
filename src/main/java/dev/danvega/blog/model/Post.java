package dev.danvega.blog.model;

import java.time.LocalDateTime;

public record Post(
        Integer id,
        String title,
        String slug,
        String content,
        LocalDateTime publishedOn
) {
}
