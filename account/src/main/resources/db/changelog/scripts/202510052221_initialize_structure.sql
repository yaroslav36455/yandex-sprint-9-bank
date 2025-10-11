CREATE TABLE IF NOT EXISTS credentials
(
    id             BIGSERIAL PRIMARY KEY,
    created_at     TIMESTAMP     NOT NULL,
    login          VARCHAR(100)  NOT NULL,
    password       VARCHAR(100)  NOT NULL,
    UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS account
(
    id             BIGSERIAL PRIMARY KEY,
    created_at     TIMESTAMP     NOT NULL,
    credentials_id BIGINT        NOT NULL,
    balance        NUMERIC(15,2) NOT NULL,
    currency       VARCHAR(20)   NOT NULL,
    UNIQUE (credentials_id, currency),
    FOREIGN KEY (credentials_id) REFERENCES credentials (id)
);

CREATE TABLE IF NOT EXISTS deferred_notification
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP   NOT NULL,
    login      VARCHAR(50) NOT NULL,
    message    TEXT        NOT NULL,
    status     VARCHAR(20) NOT NULL
);
