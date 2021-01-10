package jpastart.reserve.model

import jpastart.jpa.EMF
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class RealUserLogTest {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val review = Review("H001", 5, "최고에요")
            val realUserLog = RealUserLog(review, LocalDateTime.now())

            em.persist(review) // 여기서 Review의 아이디가 생성됨
            em.persist(realUserLog)

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
