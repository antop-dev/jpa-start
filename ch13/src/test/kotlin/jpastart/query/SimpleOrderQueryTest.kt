package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.team.Player
import org.junit.jupiter.api.Test

internal class SimpleOrderQueryTest : JpaTestBase() {

    @Test
    fun orderByAssociationId() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select p from Player p order by p.team.id, p.name", Player::class.java)
            query.resultList
        } finally {
            em.close()
        }
    }

}
