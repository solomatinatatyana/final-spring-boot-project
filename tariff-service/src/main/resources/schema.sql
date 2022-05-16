DROP TABLE IF EXISTS tariff;


create table tariff(
    id bigint AUTO_INCREMENT primary key,
    brand varchar (50),
    tariff int(20)
);