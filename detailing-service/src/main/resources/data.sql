insert into products(product_name, description, price) values ('Восстановительная полировка кузова', 'Описание',1000),
                                    ('Защитное покрытие на кузов авто', 'Описание',1000),
                                    ('Полировка фар и фонарей', 'Описание',2000),
                                    ('Тонировка фонарей пленкой', 'Описание',1500),
                                    ('Бронировка фар защитной пленкой', 'Описание',2000),
                                    ('Антихром пакет (пленкой)', 'Описание',1000),
                                    ('Предпродажная подготовка авто', 'Описание',5000),
                                    ('Мойка подкапотного пространства', 'Описание',1000),
                                    ('Полировка стекол', 'Описание',1000),
                                    ('Химчистка салона', 'Описание',3000),
                                    ('Удаление вмятин без покраски', 'Описание',4000),
                                    ('Тонировка', 'Описание',2000),
                                    ('Ремонт сколов и трещин', 'Описание',5000),
                                    ('Полная оклейка авто винил/полеуретан', 'Описание',7000),
                                    ('Покраска кожи', 'Описание',9000);


insert into models(model) values ('Octavia'),
                                 ('Rapid'),
                                 ('3'),
                                 ('5'),
                                 ('Teana'),
                                 ('Camry'),
                                 ('Civic'),
                                 ('Q5'),
                                 ('M2'),
                                 ('Rapide');


insert into cars(brand) values ('Skoda'),
                               ('Mazda'),
                               ('Nissan'),
                               ('Tayota'),
                               ('Honda'),
                               ('Audi'),
                               ('BMW'),
                               ('Aston Martin');


insert into detailing_users(username, password, phone, email, active, roles) values
     ('admin', 'password', '79056787654','admin@mail.ru', true, 'ADMIN'),
     ('manager', 'password', '79034563267', 'manager@mail.ru', true, 'MANAGER'),
     ('user','password', '79078765434', 'user@mail.ru', true, 'USER');

insert into authorities(username, roles) values ('admin', 'ADMIN'),
                                                ('manager', 'MANAGER'),
                                                ('user','USER');

insert into orders(code, car_brand_id, status, user_id, request_id) values ('QWERTY',1,'Новый',3,0),
                                                                      ('QWERTY2',3,'Новый',2,0),
                                                                      ('QWERTY3',7,'Активный',1,0),
                                                                      ('QWERTY4',3,'Завершен',3,0);

insert into orders_products(order_id, product_id) values (1,1),
                                                         (1,3),
                                                         (2,6),
                                                         (3,2),
                                                         (4,5),
                                                         (4,8);

insert into requests(first_name, phone, car_brand_id, status, comment) values ('Customer1','7907564325', 1,'Новая','Комментарий к заявке'),
                                                                      ('Customer2','79045676543',3,'Новая',''),
                                                                     ('Customer3','79045676543',3,'Подтверждена','');

insert into requests_products(request_id, product_id) values (1,1),
                                                         (1,3),
                                                         (2,6),
                                                         (3,4);




