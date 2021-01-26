## 새로운 엔티티 판단

스프링 데이터 JPA는 새로운 엔티티인지 여부를 판단할 때 다음 규칙을 사용한다.

* 해당 엔티티 클래스가 `org.springframework.data.domain.Persistable` 인터페이스를 구현했다면 `Persistable#isNew()` 메서드로 새로운 엔티티인지 검사한다.
* 엔티티에 `@Version` 속성이 있다면 버전 속성이 `null`인 경우 새 엔티티로 간주한다.
* 식별자가 기본 데이터 타입이 아니면 식별자가 `null`인 경우 새 엔티티로 간주한다. 숫자 타입이면 식별자 값이 `0`인 경우에 새 엔티티로 간주한다.

## References

* [Spring JUnit Jupiter Testing Annotations](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#integration-testing-annotations-junit-jupiter)
