package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.team.Player
import jpastart.team.Team
import org.junit.jupiter.api.Test
import javax.persistence.criteria.JoinType

class GroupByTest : JpaTestBase() {

    @Test
    fun groupBy() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Array<Any>::class.java)
            val p = cq.from(Player::class.java)

            val salary = p.get<Number>("salary")
            val teamId = p.get<Team>("team").get<String>("id")

            cq.multiselect(
                teamId, cb.count(p), cb.avg(salary), cb.max(salary), cb.min(salary)
            ).groupBy(teamId)

            val query = em.createQuery(cq)
            // select player0_.team_id as col_0_0_, count(player0_.player_id) as col_1_0_, avg(player0_.salary)
            // as col_2_0_, max(player0_.salary) as col_3_0_, min(player0_.salary) as col_4_0_
            // from Player player0_ group by player0_.team_id
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun groupBy2() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Array<Any>::class.java)
            val p = cq.from(Player::class.java)
            val t = p.join<Player, Team>("team", JoinType.LEFT)
            cq.groupBy(t)
            cq.multiselect(t, cb.count(p), cb.avg(p.get("salary")))

            val query = em.createQuery(cq)
            // select team1_.id as col_0_0_, count(player0_.player_id) as col_1_0_, avg(player0_.salary) as
            // col_2_0_, team1_.id as id1_7_, team1_.name as name2_7_ from Player player0_ left outer join
            // Team team1_ on player0_.team_id=team1_.id group by team1_.id
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun groupByHaving() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Array<Any>::class.java)
            val p = cq.from(Player::class.java)
            val t = p.join<Player, Team>("team", JoinType.LEFT)
            cq.groupBy(t)
            cq.having(cb.gt(cb.count(p), 1))
            cq.multiselect(t, cb.count(p), cb.avg(p.get("salary")))

            val query = em.createQuery(cq)
            // select team1_.id as col_0_0_, count(player0_.player_id) as col_1_0_, avg(player0_.salary) as
            // col_2_0_, team1_.id as id1_7_, team1_.name as name2_7_ from Player player0_ left outer join
            // Team team1_ on player0_.team_id=team1_.id group by team1_.id having count(player0_.player_id)>1
            query.resultList
        } finally {
            em.close()
        }
    }

}
