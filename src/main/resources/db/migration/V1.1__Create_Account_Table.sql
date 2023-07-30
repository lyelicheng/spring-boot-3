DROP TABLE IF EXISTS account;

create table account (
    id bigint NOT NULL PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
)