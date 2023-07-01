CREATE TABLE accounts(
                id bigserial PRIMARY KEY,
                login VARCHAR(255),
                keypass VARCHAR(255),
                username VARCHAR(255),
                phone VARCHAR(255),
                email VARCHAR(255),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tickets(
                id bigserial PRIMARY KEY,
                account_id bigint REFERENCES accounts(id),
                expired DATE
);