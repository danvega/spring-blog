package dev.danvega.blog.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.danvega.blog.model.json.AuthorDeserializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Post {
    @Id @JsonIgnore
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
    private final Set<Comment> comments = new HashSet<>();
    private AggregateReference<Author,Integer> author;

    public Post(String title,String content, AggregateReference<Author,Integer> author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishedOn = LocalDateTime.now();
    }

    @PersistenceCreator
    @JsonCreator
    public Post(String title, String content, LocalDateTime publishedOn, LocalDateTime updatedOn, AggregateReference<Author,Integer> author, Set<Comment> comments) {
        this.title = title;
        this.content = content;
        this.publishedOn = publishedOn;
        this.updatedOn = updatedOn;
        this.author = author;
        comments.forEach(this::addComment);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDateTime publishedOn) {
        this.publishedOn = publishedOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void addComments(List<Comment> comments) {
        comments.forEach(this::addComment);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.post = this;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public AggregateReference<Author, Integer> getAuthor() {
        return author;
    }

    public void setAuthor(AggregateReference<Author, Integer> author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishedOn=" + publishedOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}