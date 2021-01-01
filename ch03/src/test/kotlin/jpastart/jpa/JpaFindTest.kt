package jpastart.jpa

import jpastart.JpaTestBase
import jpastart.jpa.EMF.Companion.createEntityManager
import jpastart.reserve.model.Room
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import javax.persistence.EntityManager

class JpaFindTest : JpaTestBase() {

    @Test
    fun find() {
        assertThat(findRoomById("R101"), notNullValue())
        assertThat(findRoomById("NO_ID"), nullValue())
    }

    private fun findRoomById(roomId: String): Room? {
        val room: Room?
        val entityManager: EntityManager = createEntityManager()
        try {
            room = entityManager.find(Room::class.java, roomId)
        } catch (ex: Exception) {
            throw ex
        } finally {
            entityManager.close()
        }
        return room
    }
}
