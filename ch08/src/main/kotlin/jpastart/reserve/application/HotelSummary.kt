package jpastart.reserve.application

import jpastart.reserve.model.Hotel
import jpastart.reserve.model.Review

data class HotelSummary(
    val hotel: Hotel,
    val latestReviews: List<Review>
)
