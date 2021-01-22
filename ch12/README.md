## 연관의 복잡성

* 로딩 설정의 어려움 → 상황에 따라 `EAGER`/`LAZY`를 사용해야할 경우 다르다.
* 편리한 객체 탐색과 높은 결합도 → 편리함↑ 결합도↑ = 수정이 힘듬

## 연관 범위 한정과 식별자를 통한 간접 참조

```kotlin
class Order {
    val orderLines: List<OrderLine> // 영역 내 모델은 직접 참조
    val userId: String // 영역 밖의 엔티티는 식별자로 참조
}
```

`Order`에서 `User`를 직접 가져올 수 없지만 결합도가 내려간다.

## 상태 변경 관련 기능과 조회 관련 기능

저장, 수정, 삭제는 엔티티를 이용하고, 조회는 따로 구현.

예) JQPL<sup>`Java Persistence Query language`</sup>, [Mybatis](https://mybatis.org/mybatis-3/)
, [Querydsl JPA](https://github.com/querydsl/querydsl/tree/master/querydsl-jpa), [jOOQ](https://www.jooq.org/) 등..

## 식별자를 공유하는 1:1 연관이 엔티티와 밸류 관계인지 확인

관계를 잘 판단하자...

* 엔티티 + 엔티티

    ```kotlin
    @Entity
    class Appeal(
        @Id
        val id: String,
        @OneToOne(mappedBy = "appeal")
        val status: AppealStatus
    )
    
    @Entity(name = "appeal_status")
    class AppealStatus(
        @Id
        val Id: String,
        @OneToOne
        @PrimaryKeyJoinColumn
        val appeal: Appeal
    )
    ```

* 엔티티 + 밸류

    ```kotlin
    @Entity
    @SecondaryTable(
        name = "appeal_status",
        pkJoinColumns = PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
    )
    class Appeal(
        @Id
        val id: String,
        @Embedded
        val status: AppealStatus
    )
    
    @Embeddable
    class AppealStatus
    ```

## 엔티티 콜렉션 연관과 주의 사향

* 1:N 연관보다 N:1 연관 우선
* 엔티티 간 1:N 연관과 밸류 콜렉션 → `@OneToOne`를 `@ElementCollection`으로 대체 가능하다.
* M:N 연관 대체하기 : `N:1` 연관을 사용하는 중간 테이블을 사용
