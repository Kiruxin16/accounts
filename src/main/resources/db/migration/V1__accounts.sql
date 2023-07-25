CREATE TABLE client_accounts(
                id bigserial PRIMARY KEY,
                login VARCHAR(255),
                keypass VARCHAR(255),
                username VARCHAR(255),
                phone VARCHAR(255),
                email VARCHAR(255),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subscriptions(
                id bigserial PRIMARY KEY,
                number_of_workouts int,
                workouts_reserved int,
                client_id bigint REFERENCES client_accounts(id),
                discipline VARCHAR(255),
                expired DATE
);