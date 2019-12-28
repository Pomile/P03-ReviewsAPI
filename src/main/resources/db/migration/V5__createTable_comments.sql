CREATE TABLE IF NOT EXISTS COMMENTS(
    comment_id SERIAL,
    review_id INT4 NOT NULL,
    title VARCHAR NOT NULL,
    text VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT comments_comment_id_pk
        PRIMARY KEY (comment_id),
    CONSTRAINT comments_review_id_fk
        FOREIGN KEY (review_id) REFERENCES REVIEWS (review_id)
);

