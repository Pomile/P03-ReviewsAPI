CREATE TABLE IF NOT EXISTS PRODUCTS(
    id SERIAL,
    name VARCHAR NOT NULL,
    image TEXT NOT NULL,
    price numeric NOT NULL,
    stock int4 NOT NULL,
    description VARCHAR NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT products_id_pk
        PRIMARY KEY (id)
);