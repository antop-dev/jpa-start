## 테이블 생성

```mysql
create table team
(
    id   varchar(50) not null primary key,
    name varchar(50)
) engine innodb
  character set utf8;

create table player
(
    player_id varchar(50) not null primary key,
    name      varchar(50),
    salary    int,
    team_id   varchar(50),
    UNIQUE (player_id, team_id)
) engine innodb
  character set utf8;

create table performance
(
    id   varchar(50) not null primary key,
    name varchar(50)
) engine innodb
  character set utf8;

create table person
(
    id   varchar(50) not null primary key,
    name varchar(50)
) engine innodb
  character set utf8;

create table perf_person
(
    performance_id varchar(50) not null,
    person_id      varchar(50) not null,
    UNIQUE (performance_id, person_id)
) engine innodb
  character set utf8;

create table location
(
    id   varchar(50) not null primary key,
    name varchar(50) not null
) engine innodb
  character set utf8;

create table loc_eng
(
    location_id varchar(50) not null,
    list_idx    int,
    engineer_id varchar(50) not null,
    index (location_id, list_idx, engineer_id)
) engine innodb
  character set utf8;

create table engineer
(
    id   varchar(50) not null primary key,
    name varchar(50) not null
) engine innodb
  character set utf8;

create table location3
(
    id   varchar(50) not null primary key,
    name varchar(50) not null
) engine innodb
  character set utf8;

create table loc_eng3
(
    location_id varchar(50) not null,
    map_key     varchar(50),
    engineer_id varchar(50) not null,
    index (location_id, map_key, engineer_id)
) engine innodb
  character set utf8;

create table engineer3
(
    id   varchar(50) not null primary key,
    name varchar(50) not null
) engine innodb
  character set utf8;


create table location2
(
    id   varchar(50) not null primary key,
    name varchar(50) not null
) engine innodb
  character set utf8;

create table engineer2
(
    id          varchar(50) not null primary key,
    name        varchar(50) not null,
    location_id varchar(50),
    list_idx    int,
    index (location_id, list_idx, id)
) engine innodb
  character set utf8;
```
