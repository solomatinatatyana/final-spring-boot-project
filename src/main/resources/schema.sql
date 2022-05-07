DROP TABLE IF EXISTS products;

create table products(
    id long AUTO_INCREMENT primary key ,
    product_name varchar (255),
    description varchar (8000),
    price int(20)
);

create table detailing_users(
                              id long AUTO_INCREMENT primary key,
                              username varchar (50),
                              password varchar (100),
                              email varchar (100),
                              active boolean,
                              roles varchar (50)
);

create table authorities(
                            username varchar (50) references detailing_users(username),
                            roles varchar (50)
);