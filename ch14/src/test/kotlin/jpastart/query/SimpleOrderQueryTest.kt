package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.team.Player
import jpastart.jpa.team.Team
import org.junit.jupiter.api.Test

class SimpleOrderQueryTest : JpaTestBase() {

    @Test
    fun orderBy() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Player::class.java)
            val p = cq.from(Player::class.java)
            cq.select(p)

            val teamIdOrder = cb.asc(p.get<Team>("team").get<String>("id"))
            val nameOrder = cb.asc(p.get<String>("name"))
            cq.orderBy(teamIdOrder, nameOrder)

            val query = em.createQuery(cq)
            // select player0_.player_id as player_i1_6_, player0_.name as name2_6_, player0_.salary as salary3_6_,
            // player0_.team_id as team_id4_6_ from Player player0_ order by player0_.team_id asc, player0_.name asc
            query.resultList
        } finally {
            em.close()
        }
    }

}
