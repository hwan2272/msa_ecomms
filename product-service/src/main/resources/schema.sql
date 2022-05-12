create table products (
    seq bigint not null AUTO_INCREMENT,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    product_id varchar(255) not null,
    product_name varchar(255) not null,
    unit_stock integer not null,
    unit_price integer not null,
    note varchar(255),
    primary key (seq)
);
