package jpastart.jpa

import jpastart.JpaTestBase
import jpastart.reserve.model.Room
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class JpaUpdateTest : JpaTestBase() {

    @Test
    fun updateInTx() {
        run {
            val em = EMF.createEntityManager()
            val tx = em.transaction
            try {
                tx.begin()
                val room = em.find(Room::class.java, "R101")
                room?.changeName("카프리")
                tx.commit()
            } catch (e: Exception) {
                tx.rollback()
                throw e
            } finally {
                em.close()
            }
        }

        run {
            val em = EMF.createEntityManager()
            val room = em.find(Room::class.java, "R101")
            em.close()
            assertThat(room.name, `is`("카프리"))
        }

    }
}
