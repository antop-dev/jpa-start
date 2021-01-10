package jpastart.reserve.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "real_user_log2")
class RealUserLog2(
    @OneToOne
    @PrimaryKeyJoinColumn
    val review: Review,
    @Column(name = "used_date")
    val realUsingDate: LocalDateTime
) {
    @Id
    @Column(name = "review_id")
    val reviewId: Long = review.id
}
