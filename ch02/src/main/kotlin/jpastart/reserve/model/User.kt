package jpastart.reserve.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity // JPA의 엔티티임을 의미
@Table(name = "user") // 어떤 테이블과 매핑되는지 설정
data class User(
    @Id // 엔티티를 식별할 떄 사용할 프로퍼티를 지정
    val email: String,
    var name: String,
    // 매핑할 테이블의 컬럼 이름을 지정
    // 생략시 필드와 동일한 이름을 갖는 칼럼에 매핑됨
    @Column(name = "create_date")
    val createDate: LocalDateTime
) {
    fun changeName(newName: String) {
        this.name = newName
    }
}
