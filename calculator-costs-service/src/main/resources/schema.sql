DROP TABLE IF EXISTS products;

create table product_price(
    id bigint AUTO_INCREMENT primary key ,
    product_name varchar (255),
    price double(20)
);