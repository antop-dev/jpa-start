package jpastart.reserve.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class MonthChargeId(
    @Column(name = "hotel_id")
    val hotelId: String,
    @Column(name = "year_mon")
    val yearMonth: String
) : Serializable
