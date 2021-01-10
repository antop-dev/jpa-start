package jpastart.guide.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.model.User
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UserBestSightTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val user = User("hgd@hgd.co", "홍길동", LocalDateTime.now())
            val bestSight = user.createBestSight("울도국", "이상 사회")
            em.persist(user)
            em.persist(bestSight)
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
