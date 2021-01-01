package jpastart.jpa

import jpastart.JpaTestBase
import jpastart.reserve.model.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test

class JpaPersistTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val hotel = Hotel("KR-S-01", "서울호텔", Grade.STAR5)
            em.persist(hotel)
            assertThat(em.contains(hotel), `is`(true))
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun identify() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val review = Review("KR-S-01", 5, "최고에요.")
            em.persist(review)
            assertThat(review.id, `is`(not(0)))
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun sequence() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val review = Review2("KR-S-01", 5, "최고에요.")
            em.persist(review)
            assertThat(review.id, `is`(not(0)))
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun tableGenerator() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val city = City("서울")
            em.persist(city)
            assertThat(city.id, `is`(not(0)))
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
