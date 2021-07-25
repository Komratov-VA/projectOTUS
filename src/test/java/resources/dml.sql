create table client
(
    id int auto_increment primary key ,
    username VARCHAR(80) not null,
    password VARCHAR(80) not null,
    active bool default true not null
);

create unique index client_id_uindex
    on client (id);

create unique index client_username_uindex
    on client (username);

//
create table client_profile
(
    id int auto_increment,
    first_name text not null comment 'имя клиента',
    last_name text not null comment 'фамилия',
    age int not null comment 'возраст',
    gender enum('m', 'w') not null comment 'пол',
    city text not null comment 'город',
    client_id int not null comment 'ключ в таблице client',
    constraint client_profile_pk
        primary key (id),
    foreign key (client_id) references client (id)
);

create unique index client_profile_client_id_uindex
    on client_profile (client_id);

create unique index client_profile_id_uindex
    on client_profile (id);


//старое
create table `client interests`
(
    id          int auto_increment primary key ,
    hobby_name  text not null comment 'хобби клиента',
    profile_key int  not null comment 'связь с таблицей client_profile',
    foreign key (profile_key) references client_profile (id)
);


create table client_friends
(
    id int auto_increment primary key ,
    client_profile_id int not null comment 'связь из таблицы client_profile',
    id_friends int not null comment 'id подписчика',
    foreign key (client_profile_id) references client_profile (id)
);

create unique index client_friends_id_uindex
    on client_friends (id);

create unique index client_friends_client_profile_id_id_friends_uindex
	on client_friends (client_profile_id, id_friends);


ALTER DATABASE heroku_4a87725eeb589cb CHARACTER SET utf8 COLLATE utf8_unicode_ci;

ALTER TABLE client_profile CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

//очереди

create table client_post
(
    id int auto_increment primary key ,
    client_id int not null comment 'ключ в таблице client',
    post text not null comment 'пост пользователя',
    create_date datetime default now() not null,
    foreign key (client_id) references client (id)
);








