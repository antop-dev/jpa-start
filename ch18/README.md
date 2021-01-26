## 상속 계층 매핑 세가지 방식의 장단점

| 방식                                       | 장점                                                         | 단점                                                         |
| ------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 한 테이블로 매핑                           | 클래스 계층이 한 테이블을 사용하므로 매핑이 간단함<br />한 테이블만 조회하므로 성능이 좋음 | 하위 클래스에 매핑된 칼럼은 `not null`일 수 없음<br />하위 클래스를 추가하면 테이블을 변경해야 함 |
| 클래스마다 테이블로 매핑                   | 테이블마다 필요한 데이터만 보관하므로 데이터가 정규화됨      | 외부 조인을 사용하므로 계층도가 복잡해질수록 조회 성능이 떨어짐 |
| 객체 생성 가능 클래스마다 별도 테이블 매핑 | 최하위 타입으로 조회하면 조인이 발생하지 않음                | 식별자 중복 여부를 테이블 단위로 막을 수 없음<br />상위 타입의 속성이 바뀌면 모든 테이블을 변경해야 함 |

## 테이블 생성

```mysql
create table issue
(
    id              int        not null auto_increment primary key,
    issue_type      varchar(2) not null,
    issue_date      datetime,
    customer_name   varchar(50),
    customer_cp     varchar(20),
    content         text,
    closed          boolean,
    assignee_emp_id varchar(20),
    schedule_date   datetime,
    response        text
) engine innodb
  character set utf8;

create table attach_file
(
    id          varchar(255) primary key,
    name        varchar(255) not null,
    upload_date datetime     not null
) engine innodb
  character set utf8;

create table local_file
(
    id   varchar(255) primary key,
    path varchar(255)
) engine innodb
  character set utf8;

create table cloud_file
(
    id       varchar(255) primary key,
    provider varchar(255),
    url      varchar(255)
) engine innodb
  character set utf8;

create table ent_member
(
    id      varchar(100) not null primary key,
    name    varchar(100),
    comp_id varchar(100)
) engine innodb
  character set utf8;

create table personal_member
(
    id    varchar(100) not null primary key,
    name  varchar(100),
    email varchar(100)
) engine innodb
  character set utf8;

create table temp_member
(
    id          varchar(100) not null primary key,
    name        varchar(100),
    email       varchar(100),
    expire_date datetime
) engine innodb
  character set utf8;

create table auth_log
(
    id        int auto_increment PRIMARY KEY,
    userid    varchar(100),
    ipaddress varchar(100),
    timestamp datetime,
    success   boolean
) engine innodb
  character set utf8;

create table item
(
    id    varchar(100) PRIMARY KEY,
    name  varchar(100),
    price varchar(100)
) engine innodb
  character set utf8;

create table category
(
    id          varchar(100) PRIMARY KEY,
    name        varchar(100),
    crt_dtm     datetime,
    crt_empid   varchar(20),
    creation_ip varchar(16)
) engine innodb
  character set utf8;

create table seller
(
    id        varchar(100) PRIMARY KEY,
    code      varchar(100),
    crt_dtm   datetime,
    crt_empid varchar(20),
    crt_ip    varchar(16)
) engine innodb
  character set utf8;
```
