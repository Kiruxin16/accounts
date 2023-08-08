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

INSERT INTO client_accounts(login,keypass,username,phone,email) VALUES
('Grafter','595330210','Кулешов Андрей Борисович','899998887766','grafter@mail.ru'),
('Vitty','595330210','Панов Виктор Сергеевич','899998887766','vitty@mail.ru'),
('Thomp','595330210','Маратканов Анатолий Иванович','899998887766','thomp@mail.ru'),
('Alaya','595330210','Линова Алена Николаенва','899998887766','alaya@mail.ru');

CREATE TABLE subscriptions(
                id bigserial PRIMARY KEY,
                number_of_workouts int,
                workouts_reserved int,
                client_id bigint REFERENCES client_accounts(id),
                discipline VARCHAR(255),
                expired DATE
);

INSERT INTO subscriptions(number_of_workouts,workouts_reserved,client_id,discipline,expired) VALUES
(12,2,1,'fitness','29.08.2023'),
(8,0,2,'crossfit','11.09.2023'),
(3,1,3,'crossfit','07.08.2023'),
(5,2,3,'stretching','07.08.2023'),
(6,0,4,'stretching','07.08.2023'),
(1,1,4,'gym','07.08.2023'),
(5,2,4,'crossfit','07.08.2023');