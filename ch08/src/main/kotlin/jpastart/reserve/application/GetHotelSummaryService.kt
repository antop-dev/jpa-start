package jpastart.reserve.application

import jpastart.jpa.EMF
import jpastart.reserve.repository.HotelRepository
import jpastart.reserve.repository.ReviewRepository

class GetHotelSummaryService {
    private val hotelRepository = HotelRepository()
    private val reviewRepository = ReviewRepository()

    fun getHotelSummary(hotelId: String): HotelSummary {
        try {
            val hotel = hotelRepository.find(hotelId) ?: throw HotelNotFoundException()
            val reviews = reviewRepository.findByHotel(hotel, 0, 3)
            return HotelSummary(hotel, reviews)
        } finally {
            EMF.closeCurrentEntityManager()
        }
    }

}
