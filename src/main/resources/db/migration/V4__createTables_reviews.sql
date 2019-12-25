CREATE TABLE IF NOT EXISTS REVIEWS(
    id SERIAL,
    product_id INT4 NOT NULL,
    title VARCHAR NOT NULL,
    summary VARCHAR NOT NULL,
    upvote INT4 DEFAULT 0,
    downvote INT4 DEFAULT 0,
    createdAt TIMESTAMP NOT NULL DEFAULT NOW(),
    details VARCHAR,
    CONSTRAINT reviews_id_pk
        PRIMARY KEY (id),
    CONSTRAINT reviews_productId_fk
        FOREIGN KEY (product_id) REFERENCES PRODUCTS (product_id)
);
