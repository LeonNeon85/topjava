DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);


INSERT INTO meals (description, calories, user_id)
VALUES ('Завтрак User', 2500, 100000);

INSERT INTO meals (description, calories, user_id)
VALUES ('Обед User', 500, 100000);

INSERT INTO meals (description, calories, user_id)
VALUES ('Ужин User', 500, 100000);

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2015-05-06','Завтрак Admin', 1500, 100001);

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2015-05-06','Obed Admi', 1500, 100001);

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2015-05-06','Yzin Admi', 1500, 100001);

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2017-01-05','Завтрак User', 1500, 100000);

INSERT INTO meals (dateTime, description, calories, user_id)
VALUES ('2017-01-05','Завтрак Admin', 1500, 100001);
