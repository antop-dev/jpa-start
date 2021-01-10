## 테이블 생성

```mysql

create table membership_card
(
    card_number varchar(16) not null primary key,
    user_email  varchar(50),
    expiry_date date,
    enabled     boolean,
    foreign key (user_email) references user (email),
    constraint unique key (user_email)
) engine innodb
  character set utf8;

create table user_best_sight
(
    email       varchar(50) not null primary key,
    title       varchar(255),
    description text,
    foreign key (email) references user (email)
) engine innodb
  character set utf8;

create table real_user_log
(
    id        int not null auto_increment primary key,
    review_id int,
    used_date datetime
) engine innodb
  character set utf8;

create table real_user_log2
(
    review_id int,
    used_date datetime
) engine innodb
  character set utf8;
```

