package jpastart.reserve.repository

import jpastart.jpa.EMF
import jpastart.reserve.model.Hotel
import jpastart.reserve.model.Review

class ReviewRepository {

    fun findByHotel(hotel: Hotel, startRow: Int, maxResults: Int): List<Review> {
        val query = EMF.currentEntityManager().createQuery(
            ("select r from Review r" +
                    " where r.hotel = :hotel" +
                    " order by r.id desc").trimIndent(), Review::class.java
        ).apply {
            setParameter("hotel", hotel)
            firstResult = startRow
            this.maxResults = maxResults
        }
        return query.resultList
    }

}
