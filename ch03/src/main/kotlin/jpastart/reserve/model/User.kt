package jpastart.reserve.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User(
    // 필드에 적용했을 경우 모든 필드가 매핑 대상이 된다.
    @Id
    val email: String,

    val name: String,

    // 하이버네이트 5.2이상은 자바 8의 날짜/시간 타입을 지원한다.
    val createTime: LocalDateTime
)
