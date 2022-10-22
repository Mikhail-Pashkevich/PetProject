drop
    database if exists PetProject;

create
    database PetProject;

use
    PetProject;

create table user
(
    id       bigint       not null primary key auto_increment,
    login    varchar(100) not null,
    password varchar(100) not null,
    nickname varchar(100) not null
);

create table role
(
    id   bigint       not null primary key auto_increment,
    name varchar(100) not null
);

create table user_roles
(
    user_id bigint not null,
    role_id bigint not null,

    constraint pk_user_role primary key (user_id, role_id)
);

create table battle
(
    id                     bigint       not null primary key auto_increment,
    field_id               bigint       not null,
    battle_status          varchar(100) not null,
    last_activity_datetime datetime     not null,
    player_x_id            bigint,
    player_o_id            bigint
);

create table field
(
    id    bigint       not null primary key auto_increment,
    field varchar(255) not null
);


insert into user(login, password, nickname)
values ('login1', 'password1', 'nickname1'),
       ('login2', 'password2', 'nickname2'),
       ('login3', 'password3', 'nickname3');
