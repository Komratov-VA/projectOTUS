create table client_profile_fake
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
    on client_profile_fake (client_id);

create unique index client_profile_id_uindex
    on client_profile_fake (id);

-- генерация тестовых данных

SET SESSION cte_max_recursion_depth = 1000000;
INSERT client( username, password )
WITH RECURSIVE cte AS (
    SELECT 1 AS val, 1 as val2
    UNION ALL
    SELECT val + 1, val2+1
    FROM cte
    WHERE val  < 921007
)
SELECT * FROM cte;

INSERT client_profile(id, first_name,last_name, age, gender, city, client_id )
WITH RECURSIVE cte AS (
    SELECT 79009 as v, CONCAT(CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)) as c
                     ,CONCAT(CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)) as c1
                     , floor (RAND()*(90-12+1)+12) as  c2,CHAR((FLOOR(0 + (RAND() * 2)) * (109 - 119)) + 119)  as  c3,CONCAT(CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251))  as  c4, 79009

    UNION ALL
    SELECT v+1, CONCAT(CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251))
        ,CONCAT(CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251))
    , floor (RAND()*(90-12+1)+12),CHAR((FLOOR(0 + (RAND() * 2)) * (109 - 119)) + 119),CONCAT(CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
        ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)), v+1
    FROM cte
    WHERE v  < 920998
)
SELECT * FROM cte;

-- что может пригодится


SELECT floor (RAND()*(255-224+1)+224);
SET CHARACTER SET utf8mb4;
SET NAMES cp1251;
select CHAR(109) as string;
select CHAR((FLOOR(0 + (RAND() * 2)) * (109 - 119)) + 119) as string;
select (FLOOR(0 + (RAND() * 2)) * (109 - 119)) + 119;
# 224 255


select CONCAT(CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
    ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251)
    ,CHAR(floor (RAND()*(255-224+1)+224) USING cp1251),CHAR(floor (RAND()*(255-224+1)+224) USING cp1251));

select ASCII('В');

SET character_set_results='cp1251'; /*устанавливает кодировку данных отправляемых К клиента*/
SET character_set_client='cp1251';  /*устанавливает кодировку данных отправляемых ОТ клиенту*/
SET character_set_connection='cp1251';

show variables like 'char%';
show variables like 'collation%';


SET CHARACTER SET utf8mb4;
