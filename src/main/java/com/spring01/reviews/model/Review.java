package com.spring01.reviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "reviews")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long Id;

    @NotNull(message = "title field must be provided")
    @NotBlank(message = "title cannot be empty")
    @Column(name = "title")
    private String title;

    @NotNull(message = "summary field must be provided")
    @NotBlank(message = "summary cannot be empty")
    @Column(name = "summary")
    private String summary;

    @Column(name = "details")
    private String details = "";

    @Column(name = "upvote")
    private Integer upvote = 0;

    @Column(name = "downvote")
    private Integer downvote = 0;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name="product_id", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="review")
    List<Comment> comments;

    public Review() { }

    public Review(String title, String summary) {
        this.title = title;
        this.summary = summary;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id ;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getUpvote() {
        return upvote;
    }

    public Integer getDownvote() {
        return downvote;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setUpvote(Integer upvote) {
        this.upvote = upvote;
    }

    public void setDownvote(Integer downvote) {
        this.downvote = downvote;
    }

    public String getDetails() {
        return details;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
