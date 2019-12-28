package com.spring01.reviews.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Entity
@Table(name="comments")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long Id;

    @Column(name = "review_id")
    private Long reviewId;

    @NotNull(message = "title field must be provided")
    @NotBlank(message = "title cannot be empty")
    @Column(name = "title")
    private String title;

    @NotNull(message = "text field must be provided")
    @NotBlank(message = "text cannot be empty")
    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private ZonedDateTime timestamp = ZonedDateTime.now();

    @ManyToOne
    @JoinColumn(name = "review_id", insertable = false, updatable = false)
    @JsonIgnore
    private Review review;

    public Comment() { }

    public Comment(String title, String text) {
        this.title = title;
        this.text = text;
    }



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}