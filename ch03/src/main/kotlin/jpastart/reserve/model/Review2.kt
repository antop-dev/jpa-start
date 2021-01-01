package jpastart.reserve.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "hotel_review2")
class Review2(
    @Column(name = "hotel_id")
    val hotelId: String,

    val rate: Int,

    val comment: String
) {
    @Id
    @SequenceGenerator(name = "review_seq_gen", sequenceName = "hotel_review_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq_gen")
    // generator 를 직접 시퀀스명으로 명시할 경우 아래와 같은 에러 발생
    // The increment size of the [hotel_review_seq] sequence is set to [50] in the entity mapping while the associated database sequence increment size is [1].
    // @SequenceGenerator 애노테이션을 생성해서 allocationSize 값을 1로 설정해야 한다.
    var id: Long = 0
        private set

    @Column(name = "rating_date")
    val ratingDate: LocalDateTime = LocalDateTime.now()
}
