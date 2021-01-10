package jpastart.reserve.model

import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class RealUserLog2Test {

    @Test
    fun `incorrect usage`() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val review = Review("H001", 5, "최고에요")
            val realUserLog = RealUserLog2(review, LocalDateTime.now())

            em.persist(review)
            em.persist(realUserLog)

            assertThat(realUserLog.reviewId, `is`(0)) // Review 아이디가 할당되지 않았다.
            assertThat(realUserLog.reviewId, not(`is`(review.id)))

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun `correct usage`() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val review = Review("H001", 5, "최고에요")
            em.persist(review)

            val realUserLog = RealUserLog2(review, LocalDateTime.now())

            // 키를 공유하는 경우 반드시 Review 를 먼저 insert 되게 하자
            // em.persist(review)
            em.persist(realUserLog)

            assertThat(realUserLog.reviewId, not(`is`(0))) // Review 아이디가 할당 되었다.
            assertThat(realUserLog.reviewId, `is`(review.id))

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
