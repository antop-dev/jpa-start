package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

internal class GroupByTest : JpaTestBase() {

    @Test
    fun groupBy() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select p.team.id, count(p), avg(p.salary), max(p.salary), min(p.salary) " +
                        "from Player p group by p.team.id", Array<Any>::class.java
            )
            // select player0_.team_id as col_0_0_, count(player0_.player_id) as col_1_0_, avg(player0_.salary)
            // as col_2_0_, max(player0_.salary) as col_3_0_, min(player0_.salary) as col_4_0_ from Player
            // player0_ group by player0_.team_id
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun groupBy2() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select t, count(p), avg(p.salary), max(p.salary), min(p.salary) " +
                        "from Player p left join p.team t group by t", Array<Any>::class.java
            )
            // select team1_.id as col_0_0_, count(player0_.player_id) as col_1_0_, avg(player0_.salary) as
            // col_2_0_, max(player0_.salary) as col_3_0_, min(player0_.salary) as col_4_0_, team1_.id as
            // id1_4_, team1_.name as name2_4_ from Player player0_ left outer join Team team1_ on player0_.team_id=team1_.id
            // group by team1_.id
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun groupByHaving() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select t, count(p), avg(p.salary) " +
                        "from Team t left join t.players p " +
                        "group by t having count(p) > 1", Array<Any>::class.java
            )
            // select team0_.id as col_0_0_, count(players1_.player_id) as col_1_0_, avg(players1_.salary)
            // as col_2_0_, team0_.id as id1_4_, team0_.name as name2_4_ from Team team0_ left outer join
            // Player players1_ on team0_.id=players1_.team_id group by team0_.id having count(players1_.player_id)>1
            query.resultList
        } finally {
            em.close()
        }
    }

}
