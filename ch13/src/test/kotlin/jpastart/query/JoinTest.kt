package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.team.Player
import org.junit.jupiter.api.Test

internal class JoinTest : JpaTestBase() {

    @Test
    fun implicitJoin() {
        val em = EMF.createEntityManager()
        try {
            val query =
                em.createQuery(
                    "select p from Player p where p.team.name = :teamName", Player::class.java
                ).apply {
                    setParameter("teamName", "T1")
                }
            // select player0_.player_id as player_i1_3_, player0_.name as name2_3_, player0_.salary as salary3_3_,
            // player0_.team_id as team_id4_3_ from Player player0_ cross join Team team1_ where player0_.team_id=team1_.id
            // and team1_.name='T1'
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun join() {
        val em = EMF.createEntityManager()
        try {
            val query =
                em.createQuery(
                    "select p from Player p join p.team t where t.name = :teamName",
                    Player::class.java
                ).apply {
                    setParameter("teamName", "T1")
                }
            // select player0_.player_id as player_i1_3_, player0_.name as name2_3_, player0_.salary as salary3_3_,
            // player0_.team_id as team_id4_3_ from Player player0_ inner join Team team1_ on player0_.team_id=team1_.id
            // where team1_.name='T1'
            query.resultList
        } finally {
            em.close()
        }
    }

}
