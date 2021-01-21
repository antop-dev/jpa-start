package jpastart.jpa.reserve.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class User(
    @Id
    val email: String,

    val name: String,
    @Column(name = "create_date")
    val createTime: LocalDateTime
) {
    @OneToOne(mappedBy = "owner")
    var card: MembershipCard? = null

    fun issue(card: MembershipCard) {
        card.assignTo(this)
        this.card = card
    }

}
