package com.spring01.reviews.DAO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewDao implements Serializable {
    private Long id;
    private String title;
    private String summary;
    private String details;
    private Integer upvote;
    private Integer downvote;
    private Long product;

    public ReviewDao(Long id,
                     String title,
                     String summary,
                     String details,
                     Integer upvote,
                     Integer downvote) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.details = details;
        this.upvote = upvote;
        this.downvote = downvote;
        this.product = product;
    }

    public ReviewDao(Long id,
                     String title,
                     String summary,
                     String details,
                     Integer upvote,
                     Integer downvote,
                     Long product) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.details = details;
        this.upvote = upvote;
        this.downvote = downvote;
        this.product = product;
    }
}