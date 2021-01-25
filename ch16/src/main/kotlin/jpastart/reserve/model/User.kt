package jpastart.reserve.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User(
    @Id
    val email: String,

    var name: String,
    @Column(name = "create_date")
    val createDate: LocalDateTime
) {
    fun changeName(newName: String) {
        this.name = newName
    }
}
