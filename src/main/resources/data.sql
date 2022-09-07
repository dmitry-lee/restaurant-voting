INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@yandex.ru', '{noop}password');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANT (name)
VALUES ('Italy'),
       ('Capuletti'),
       ('Плюшкин');

INSERT INTO DISH (name, menu_date, price, restaurant_id)
VALUES ('Bruschetta with chicken pate and cream cheese', '2022-09-01', 390, 1),
       ('Greek salad with chickpeas and feta', '2022-09-01', 690, 1),
       ('Tom yam with coconut milk and shrimp', '2022-09-01', 850, 1),
       ('Salmon tartare', now(), 890, 2),
       ('Spaghetti with cherry tomatoes and basil', now(), 390, 2),
       ('Risotto with seafood', now(), 990, 2),
       ('Борщ с говядиной, черным хлебом и салом', now(), 530, 3),
       ('Манты с телятиной', now(), 470, 3),
       ('Бефстроганов с картофельным пюре', now(), 900, 3);

INSERT INTO VOTE (date_registered, user_id, restaurant_id)
VALUES ('2022-09-01', 1, 3),
       ('2022-09-01', 2, 1),
       (now(), 1, 1),
       (now(), 2, 3);