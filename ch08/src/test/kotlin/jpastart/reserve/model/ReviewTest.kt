package jpastart.reserve.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

class ReviewTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val hotel = em.find(Hotel::class.java, "H100-01")
            val review = Review(hotel, 5, "매우 좋았음")
            em.persist(review)

            assertThat(review.id, notNullValue())

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun findById() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val review1 = em.find(Review::class.java, 1L) // query
            val review2 = em.find(Review::class.java, 2L) // query

            val hotel1 = review1.hotel // query
            val hotel2 = review2.hotel // no query

            assertThat(hotel1 == hotel2, `is`(true))

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun reviewList() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val hotel = em.find(Hotel::class.java, "H100-01")
            val query = em.createQuery(
                ("select r from Review r" +
                        " where r.hotel = :hotel" +
                        " order by r.id desc").trimIndent(), Review::class.java
            ).apply {
                setParameter("hotel", hotel)
                firstResult = 3
                maxResults = 3
            }

            val reviews = query.resultList
            assertThat(reviews, hasSize(3))

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
