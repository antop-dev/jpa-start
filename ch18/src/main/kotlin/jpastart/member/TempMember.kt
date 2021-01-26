package jpastart.member

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "temp_member")
class TempMember(id: String, name: String, email: String) : PersonalMember(id, name, email) {
    @Column(name = "expire_date")
    var expireDate: LocalDateTime? = null
}
