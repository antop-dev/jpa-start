## 동시 접근과 잠금

※ 이지미 출처: https://stackoverflow.com/questions/129329/optimistic-vs-pessimistic-locking

![](https://i.imgur.com/EqWKugx.png)

### 선점 잠금<sup>`pessimistic lock`

![](https://i.imgur.com/lAtKbii.png)

먼저 데이터에 접근한 트랜잭션이 우선순위(read lock)를 갖는다. 다른 트랜잭션들은 **대기**한다.

주의할 점은 교착 상태<sup>`deadlock`</sup>에 빠질 수 있다.

### 비선점 잠금 (optimistic lock)

![](https://i.imgur.com/N0QZvvf.png)

먼저 데이터를 수정한 트랜잭션이 우선순위를 갖는다.

* 버전 값을 저장할 칼럼 필요 (int, Integer, short, Short, long. Long, java.sql.Timestamp)
* 버전 칼럼과 매핑할 속성에 `@Version` 애노테이션을 설정

만약 update 쿼리 실행 결과로 변경된 행 개수가 0이면, 즉 다른 트랜잭션이 데이터를 먼저 수정해서 버전 값이 현재 엔티티와 일치하지 않으면 트랜잭션을 롤백하고 익셉션을 발생한다.

새로운 엔티티를 저장할 때 버전을 지정하지 않으면 `0`을 사용한다. `null`도 `0`을 사용한다.

## 테이블 생성

```mysql
create table account
(
    account_num varchar(20) not null primary key,
    balance     integer
) engine innodb
  character set utf8;

create table customer
(
    id          varchar(20) not null primary key,
    ver         integer,
    secret_code varchar(10)
) engine innodb
  character set utf8;
```
