package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.Review
import jpastart.reserve.Review_
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class StaticMetaModelTest : JpaTestBase() {

    @Test
    fun noStaticModel() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Review::class.java)
            val r = cq.from(Review::class.java)

            val fromDate = LocalDateTime.of(2016, 6, 1, 0, 0, 0)
            val toDate = LocalDateTime.of(2016, 6, 5, 0, 0, 0)

            cq.select(r).where(cb.between(r.get("ratingDate"), fromDate, toDate))

            val query = em.createQuery(cq)

            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun staticMetaModel() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Review::class.java)
            val r = cq.from(Review::class.java)

            val fromDate = LocalDateTime.of(2016, 6, 1, 0, 0, 0)
            val toDate = LocalDateTime.of(2016, 6, 5, 0, 0, 0)

            cq.select(r).where(cb.between(r.get(Review_.ratingDate), fromDate, toDate))

            val query = em.createQuery(cq)

            query.resultList
        } finally {
            em.close()
        }
    }

}
