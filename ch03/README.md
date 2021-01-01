## 엔티티 클래스의 제약 조건

1. 클래스는 인자가 없는 기본 생성자를 제공해야 한다. 코틀린의
   경우 [kotlin-jpa](https://kotlinlang.org/docs/reference/compiler-plugins.html#no-arg-compiler-plugin) 플러그인 사용.
2. 엔티티는 클래스여야 한다.
3. 엔티티 클래스는 `final`이면 안된다.

## Kotlin + getReference()

코틀린으로 `getReference()`를 사용하려면 모든 항목에 `open`이어야 한다. 이렇게 하지 않으면 프록시가 적용되지 않는다.

```kotlin
@Entity
open class Hotel(
    @Id
    open val id: String,

    open val name: String,

    // 열거 타입에 대한 기본 매핑 설정은 @Enumerated(EnumType.ORDINAL)이므로,
    // 상수 이름을 값으로 매핑하려면 @Enumerated(EnumType.STRING)을 명시적으로 설정해야 함
    @Enumerated(EnumType.STRING)
    open val grade: Grade

) {

}
```

## 시퀀스 사용 방식 + MariaDB 10.3↑

https://vladmihalcea.com/mariadb-10-3-database-sequences/

방언(Dialect)을 `org.hibernate.dialect.MariaDB103Dialect`로 설정한 후 아래와 같이 코드 작성.

```kotlin
@Entity
@Table(name = "hotel_review")
class Review(
    // ...
) {
    @Id
    @SequenceGenerator(name = "review_seq_gen", sequenceName = "hotel_review_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq_gen")
    // generator 를 직접 시퀀스명으로 명시할 경우 아래와 같은 에러 발생
    // The increment size of the [hotel_review_seq] sequence is set to [50] in the entity mapping while the associated database sequence increment size is [1].
    // @SequenceGenerator 애노테이션을 생성해서 allocationSize 값을 1로 설정해야 한다.
    var id: Long = 0
        private set
}
```

## 테이블 생성

```sql
create table room_info
(
    id          int         not null auto_increment primary key,
    number      varchar(50) not null,
    name        varchar(50),
    description varchar(255),
    create_time datetime,
    unique key (number)
) engine innodb
  character set utf8;

create table hotel
(
    id    varchar(100) not null primary key,
    name  varchar(50),
    grade varchar(255)
) engine innodb
  character set utf8;

create table id_gen
(
    entity varchar(100) not null primary key,
    nextid int
) engine innodb
  character set utf8;

create table city
(
    id   int not null primary key,
    name varchar(200)
) engine innodb
  character set utf8;

create table hotel_review
(
    id          int          not null auto_increment primary key,
    hotel_id    varchar(100) not null,
    rate        int          not null,
    comment     varchar(255) not null,
    rating_date datetime
) engine innodb
  character set utf8;

/* 시퀀스 사용 방식 */
create table hotel_review2
(
    id          int primary key,
    hotel_id    varchar(100) not null,
    rate        int          not null,
    comment     varchar(255) not null,
    rating_date datetime
) engine innodb
  character set utf8;

create sequence hotel_review_seq;


/* 테이블 사용 방식 */
create table id_gen
(
    entity varchar(100) not null primary key,
    nextid int
) engine innodb
  character set utf8;
```

