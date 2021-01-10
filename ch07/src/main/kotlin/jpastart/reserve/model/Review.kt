package jpastart.reserve.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "hotel_review")
class Review(
    @Column(name = "hotel_id")
    val hotelId: String,

    val rate: Int,

    val comment: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "rating_date")
    val ratingDate: LocalDateTime = LocalDateTime.now()
}
