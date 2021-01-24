package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.Review
import org.junit.jupiter.api.Test

internal class PagingTest : JpaTestBase() {

    @Test
    fun paging() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select r from Review r where r.hotel.id = :hotelId order by r.id desc",
                Review::class.java
            ).apply {
                setParameter("hotelId", "H-001")
                firstResult = 10
                maxResults = 5
            }
            // select review0_.id as id1_2_, review0_.comment as comment2_2_, review0_.hotel_id as hotel_id5_2_,
            // review0_.rate as rate3_2_, review0_.rating_date as rating_d4_2_ from hotel_review review0_
            // where review0_.hotel_id='H-001' order by review0_.id desc limit 10, 5
            query.resultList
        } finally {
            em.close()
        }
    }

}
