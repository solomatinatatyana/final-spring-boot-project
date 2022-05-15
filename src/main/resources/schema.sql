DROP TABLE IF EXISTS orders_products;
DROP TABLE IF EXISTS requests_products;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS cars;

create table detailing_users(
    id bigint AUTO_INCREMENT primary key,
    username varchar (50),
    password varchar (100),
    phone varchar(100),
    email varchar (100),
    active boolean,
    roles varchar (50),
    unique key unique_uk_1(phone)
);

create table cars(
    id bigint AUTO_INCREMENT primary key ,
    brand varchar (255)
);

create table products(
    id bigint AUTO_INCREMENT primary key ,
    product_name varchar (255),
    description varchar (8000),
    price int(20)
);

create table requests(
    id bigint AUTO_INCREMENT primary key ,
    first_name varchar (255),
    phone varchar (50),
    car_brand_id bigint references cars(id),
    status varchar (50),
    comment varchar(255)
);

create table orders(
    id bigint AUTO_INCREMENT primary key ,
    code varchar (50),
    car_brand_id bigint references cars(id),
    status varchar (50),
    total double (50),
    request_id bigint,
    user_id bigint references detailing_users(id),
    customer_name varchar(50),
    unique key unique_uk_2(code)
);

create table orders_products (
    order_id bigint,
    product_id bigint,
    FOREIGN KEY(order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY(product_id) REFERENCES products(id) ON DELETE CASCADE
);



create table requests_products (
   request_id bigint,
   product_id bigint,
   FOREIGN KEY(request_id) REFERENCES requests(id) ON DELETE CASCADE,
   FOREIGN KEY(product_id) REFERENCES products(id) ON DELETE CASCADE
);

create table authorities(
    username varchar (50) references detailing_users(username),
    roles varchar (50)
);