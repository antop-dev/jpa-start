package jpastart.reserve

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "hotel_review")
class Review(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    val hotel: Hotel,

    val rate: Int,

    val comment: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "rating_date")
    val ratingDate: LocalDateTime = LocalDateTime.now()
}
