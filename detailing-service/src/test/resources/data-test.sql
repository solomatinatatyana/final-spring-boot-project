insert into products(product_name, description, price) values ('Восстановительная полировка кузова', 'Описание',1000),
                                                              ('Защитное покрытие на кузов авто', 'Описание',1000),
                                                              ('Полировка фар и фонарей', 'Описание',2000),
                                                              ('Тонировка фонарей пленкой', 'Описание',1500);


insert into cars(brand) values ('Skoda'),
                               ('Mazda'),
                               ('Nissan'),
                               ('Tayota');


insert into detailing_users(username, password, phone, email, active, roles) values
                                                                                 ('admin', 'password', '79056787654','admin@mail.ru', true, 'ADMIN'),
                                                                                 ('manager', 'password', '79034563267', 'manager@mail.ru', true, 'MANAGER'),
                                                                                 ('user','password', '79078765434', 'user@mail.ru', true, 'USER');


insert into orders(code, car_brand_id, status, user_id, request_id) values ('QWERTY',1,'Новый',3,0),
                                                                           ('QWERTY2',3,'Новый',2,0),
                                                                           ('QWERTY3',2,'Активный',1,0),
                                                                           ('QWERTY4',3,'Завершен',3,0);

insert into orders_products(order_id, product_id) values (1,1),
                                                         (1,3),
                                                         (2,3),
                                                         (3,2),
                                                         (4,1),
                                                         (4,2);

insert into requests(first_name, phone, car_brand_id, status, comment) values ('Customer1','7907564325', 1,'Новая','Комментарий к заявке'),
                                                                              ('Customer2','79045676543',3,'Новая',''),
                                                                              ('Customer3','79045676543',3,'Подтверждена','');

insert into requests_products(request_id, product_id) values (1,1),
                                                             (1,3),
                                                             (2,2),
                                                             (3,4);




