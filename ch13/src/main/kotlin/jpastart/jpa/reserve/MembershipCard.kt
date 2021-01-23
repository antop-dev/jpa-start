package jpastart.jpa.reserve

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "membership_card")
class MembershipCard(
    @Id
    @Column(name = "card_number")
    val number: String,

    owner: User,

    @Column(name = "expiry_date")
    val expiryDate: LocalDateTime
) {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_email")
    var owner: User? = owner

    var enabled: Boolean = true

}
