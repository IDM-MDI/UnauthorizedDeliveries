CREATE TABLE items
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    description      VARCHAR(255),
    amount           INT                   NOT NULL,
    currency         VARCHAR(255),
    measurement_unit VARCHAR(255),
    CONSTRAINT pk_items PRIMARY KEY (id)
);
CREATE TABLE users
(
    username    VARCHAR(255) NOT NULL,
    application VARCHAR(255),
    job         VARCHAR(255),
    department  VARCHAR(255),
    active      BOOLEAN,
    CONSTRAINT pk_users PRIMARY KEY (username)
);
CREATE TABLE postings
(
    id            BIGINT NOT NULL,
    item_id       BIGINT,
    username      VARCHAR(255),
    item_position BIGINT NOT NULL,
    quantity      INT    NOT NULL,
    contract_date date,
    posting_date  date,
    CONSTRAINT pk_postings PRIMARY KEY (id)
);
ALTER TABLE postings
    ADD CONSTRAINT FK_POSTINGS_ON_ITEM FOREIGN KEY (item_id) REFERENCES items (id);

ALTER TABLE postings
    ADD CONSTRAINT FK_POSTINGS_ON_USERNAME FOREIGN KEY (username) REFERENCES users (username);