CREATE TABLE users
(
    username    VARCHAR(255) NOT NULL,
    application VARCHAR(255) NOT NULL,
    job         VARCHAR(255) NOT NULL,
    department  VARCHAR(255) NOT NULL,
    active      BOOLEAN      NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (username)
);

CREATE TABLE items
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    description VARCHAR(255)          NOT NULL,
    amount      INT                   NOT NULL,
    currency    VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_items PRIMARY KEY (id)
);

CREATE TABLE document_headers
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    posting_number     BIGINT                NOT NULL,
    username           VARCHAR(255)          NOT NULL,
    authorize_delivery BOOLEAN               NOT NULL,
    contract_date      date                  NOT NULL,
    posting_date       date                  NOT NULL,
    CONSTRAINT pk_document_headers PRIMARY KEY (id)
);
CREATE TABLE posting_materials
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    item_id          BIGINT                NOT NULL,
    item_position    BIGINT                NOT NULL,
    quantity         INT                   NOT NULL,
    measurement_unit VARCHAR(255)          NOT NULL,
    posting_id       BIGINT                NOT NULL,
    CONSTRAINT pk_posting_materials PRIMARY KEY (id)
);
CREATE TABLE postings
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    header_id BIGINT,
    CONSTRAINT pk_postings PRIMARY KEY (id)
);

ALTER TABLE items
    ADD CONSTRAINT uc_items_description UNIQUE (description,amount);
ALTER TABLE postings
    ADD CONSTRAINT FK_POSTINGS_ON_HEADER FOREIGN KEY (header_id) REFERENCES document_headers (id);
ALTER TABLE posting_materials
    ADD CONSTRAINT FK_POSTING_MATERIALS_ON_ITEM FOREIGN KEY (item_id) REFERENCES items (id);
ALTER TABLE posting_materials
    ADD CONSTRAINT FK_POSTING_MATERIALS_ON_POSTING FOREIGN KEY (posting_id) REFERENCES postings (id);
ALTER TABLE document_headers
    ADD CONSTRAINT FK_DOCUMENT_HEADERS_ON_USERNAME FOREIGN KEY (username) REFERENCES users (username);