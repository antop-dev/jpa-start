# JPA 프로그래밍 입문

![](https://i.imgur.com/jyBKOqS.png)

https://www.kame.co.kr/nkm/detail.php?tcode=299&tbook_jong=3

* MariaDB Server 10.5.8
* Kotlin 1.4.21
* Hibernate 5.4.27.Final
* JUnit 5.7.0

위 기술로 작성한다.

## 데이터베이스 생성

```mysql
CREATE DATABASE jpastart CHARACTER SET utf8;

CREATE USER 'jpauser'@'localhost' IDENTIFIED BY 'jpapass';
CREATE USER 'jpauser'@'%' IDENTIFIED BY 'jpapass';

GRANT ALL PRIVILEGES ON jpastart.* TO 'jpauser'@'localhost';
GRANT ALL PRIVILEGES ON jpastart.* TO 'jpauser'@'%';
```

## References

* [kotlin(+JPA) entity 에서 setter 를 막을 수 있을까](https://multifrontgarden.tistory.com/272)
