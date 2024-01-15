create table users
(
    login    varchar(50) not null,
    email    varchar(50) not null,
    password varchar(500),
    role     varchar(255) check (role in ('ADMIN', 'COURIER', 'CLIENT')),
    primary key (login)
);

create table orders
(
    order_id      bigserial    not null,
    order_address varchar(255) not null,
    description   varchar(500),
    order_status  smallint     not null check (order_status between 0 and 4),
    client        varchar(50),
    courier       varchar(50),
    primary key (order_id),
    foreign key (client) references users,
    foreign key (courier) references users
);

create index order_id_index on orders (order_id);
create index client_index on orders (client);
create index courier_index on orders (courier);
create index user_login_index on users (login);
-- alter table if exists users drop constraint if exists UK_6dotkott2kjsp8vw4d0m25fb7;

insert into users(email, login, password, role)
values ('admin@gmail.com', 'admin', '$2a$08$2A6fsOcRUzal6ziA7KKlUuh9QetGxSELmBd8X6zs.vLKxwzTpHgwK', 'ADMIN');

insert into users(email, login, password, role)
values ('client@gmail.com', 'client', '$2a$08$2A6fsOcRUzal6ziA7KKlUuh9QetGxSELmBd8X6zs.vLKxwzTpHgwK', 'CLIENT');

insert into users(email, login, password, role)
values ('courier@gmail.com', 'courier', '$2a$08$2A6fsOcRUzal6ziA7KKlUuh9QetGxSELmBd8X6zs.vLKxwzTpHgwK', 'COURIER');
