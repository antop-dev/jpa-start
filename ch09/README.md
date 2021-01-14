## 테이블생성

```mysql
create table user_keyword
(
    user_email varchar(50),
    keyword    varchar(50),
    index (user_email, keyword)
) engine innodb
  character set utf8;

create table hotel_property
(
    hotel_id   varchar(100),
    prop_name  varchar(50),
    prop_value varchar(255),
    index (hotel_id, prop_name)
) engine innodb
  character set utf8;

create table hotel_property2
(
    hotel_id   varchar(100),
    prop_name  varchar(50),
    prop_value varchar(255),
    type       varchar(255),
    index (hotel_id, prop_name)
) engine innodb
  character set utf8;

create table sight_rec_item
(
    sight_id int not null,
    name     varchar(255),
    type     varchar(255),
    index (sight_id, name, type)
) engine innodb
  character set utf8;

create table itinerary
(
    id          int not null auto_increment primary key,
    name        varchar(255),
    description varchar(255)
) engine innodb
  character set utf8;

create table itinerary_site
(
    itinerary_id int,
    list_idx     int,
    site         varchar(255),
    time         varchar(255),
    constraint primary key (itinerary_id, list_idx)
) engine innodb
  character set utf8;
```
