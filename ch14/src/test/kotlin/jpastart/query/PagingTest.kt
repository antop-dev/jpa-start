package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.reserve.Hotel
import jpastart.jpa.reserve.Review
import org.junit.jupiter.api.Test

class PagingTest : JpaTestBase() {

    @Test
    fun paging() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Review::class.java)
            val r = cq.from(Review::class.java)

            cq.select(r).where(cb.equal(r.get<Hotel>("hotel").get<String>("id"), "H-001"))
            val query = em.createQuery(cq).apply {
                firstResult = 10
                maxResults = 5
            }
            // select review0_.id as id1_2_, review0_.comment as comment2_2_, review0_.hotel_id as hotel_id5_2_,
            // review0_.rate as rate3_2_, review0_.rating_date as rating_d4_2_ from hotel_review review0_
            // where review0_.hotel_id='H-001' limit 10, 5
            query.resultList
        } finally {
            em.close()
        }
    }

}
