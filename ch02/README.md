## 테이블 생성

```mysql
CREATE TABLE JPASTART.USER
(
    email       VARCHAR(50) NOT NULL PRIMARY KEY,
    name        VARCHAR(50),
    create_date DATETIME
) ENGINE INNODB
  CHARACTER SET utf8;
```

## 더티 체킹(dirty checking)

JPA는 이렇게 객체의 변경 내역을 추적해서 트랜잭션을 종료할 때 변경된 값을 DB에 반영한다.
