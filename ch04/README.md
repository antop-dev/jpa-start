## 테이블 생성

```mysql
create table hotel
(
    id       varchar(100) not null primary key,
    name     varchar(50),
    grade    varchar(255),
    /* 추가 */
    zipcode  varchar(5),
    address1 varchar(255),
    address2 varchar(255)
) engine innodb
  character set utf8;

create table sight
(
    id          int          not null auto_increment primary key,
    name        varchar(100) not null,
    zipcode     varchar(5),
    address1    varchar(100),
    address2    varchar(100),
    eng_zipcode varchar(5),
    eng_addr1   varchar(100),
    eng_addr2   varchar(100)
) engine innodb
  character set utf8;

create table sight_detail
(
    sight_id   int not null primary key,
    hours_op   varchar(255),
    holidays   varchar(255),
    facilities varchar(255)
) engine innodb
  character set utf8;

create table city
(
    id       int not null primary key,
    name     varchar(200),
    /* 추가 */
    ct_phone varchar(255),
    ct_email varchar(255),
    ct_zip   varchar(255),
    ct_addr1 varchar(255),
    ct_addr2 varchar(255)
) engine innodb
  character set utf8;

create table month_charge
(
    hotel_id   varchar(255) not null,
    year_mon   varchar(255) not null,
    charge_amt int,
    unpay_amt  int,
    primary key (hotel_id, year_mon)
) engine innodb
  character set utf8;
```

