package jpastart.jpa

import jpastart.JpaTestBase
import jpastart.reserve.model.Room
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class JpaRemoveTest : JpaTestBase() {

    @Test
    fun remove() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val room = em.find(Room::class.java, "R101")
            assertThat(em.contains(room), `is`(true))
            room?.run { em.remove(room) }
            assertThat(em.contains(room), `is`(false))
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
