CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL
);

CREATE TABLE categories
(
    id      SERIAL PRIMARY KEY,
    user_id INTEGER                                           NOT NULL,
    title   VARCHAR(255)                                      NOT NULL,
    type    VARCHAR(50) CHECK (type IN ('INCOME', 'EXPENSE')) NOT NULL,
    budget  BIGINT DEFAULT NULL,
    UNIQUE (user_id, title),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE transactions
(
    id          SERIAL PRIMARY KEY,
    category_id INTEGER                             NOT NULL,
    amount      BIGINT                              NOT NULL,
    datetime    TIMESTAMP DEFAULT current_timestamp NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);
