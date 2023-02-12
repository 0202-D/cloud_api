create table cloud.users
(
    id       serial primary key,
    login    varchar unique not null ,
    token    varchar,
    password varchar
);
insert into cloud.users
values (1, 'user', null, 'user');
create table cloud.files
(
    id      serial primary key,
    name    varchar unique ,
    size    bigint,
    content bytea,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES cloud.users
);
alter table cloud.files add column create_date date;

