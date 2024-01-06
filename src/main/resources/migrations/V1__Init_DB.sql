create table orders (
    order_id bigserial not null,
    order_address varchar(255) not null,
    client_id bigint,
    courier_id bigint,
    description varchar(500),
    order_status smallint check (order_status between 0 and 4), primary key (order_id)
);
create table users (
    id bigserial not null,
    email varchar(50) not null,
    login varchar(50) not null,
    password varchar(500) not null,
    role varchar(255) check (role in ('ADMIN','COURIER','CLIENT')), primary key (id)
);
create index order_id_index on orders (order_id);
create index client_id_index on orders (client_id);
create index courier_id_index on orders (courier_id);
create index user_id_index on users (id);
create index user_login_index on users (login);
create index user_email_index on users (email);

-- alter table if exists users drop constraint if exists UK_6dotkott2kjsp8vw4d0m25fb7;

insert into users(email, login, password, role)
values ('admin@gmail.com', 'admin', '$2a$08$2A6fsOcRUzal6ziA7KKlUuh9QetGxSELmBd8X6zs.vLKxwzTpHgwK', 'ADMIN');

insert into users(email, login, password, role)
values ('client@gmail.com', 'client', '$2a$08$2A6fsOcRUzal6ziA7KKlUuh9QetGxSELmBd8X6zs.vLKxwzTpHgwK', 'CLIENT');

insert into users(email, login, password, role)
values ('courier@gmail.com', 'courier', '$2a$08$2A6fsOcRUzal6ziA7KKlUuh9QetGxSELmBd8X6zs.vLKxwzTpHgwK', 'COURIER');
