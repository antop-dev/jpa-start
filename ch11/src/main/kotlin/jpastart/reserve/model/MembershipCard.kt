package jpastart.reserve.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "membership_card")
class MembershipCard(
    @Id
    @Column(name = "card_number")
    val number: String,

    owner: User,

    @Column(name = "expiry_date")
    val expiryDate: LocalDateTime
) {
    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "user_email")
    var owner: User? = owner

    var enabled: Boolean = true

    fun assignTo(owner: User) {
        if (this.owner != null) throw AlreadyAssignedCardException()
        this.owner = owner
    }

    fun cancelAssignment() {
        owner = null
    }

}
