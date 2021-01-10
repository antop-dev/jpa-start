package jpastart.reserve.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "real_user_log")
class RealUserLog(
    @OneToOne
    @JoinColumn(name = "review_id")
    val review: Review,
    @Column(name = "used_date")
    val realUsingDate: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
