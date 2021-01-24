package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.team.Player
import jpastart.team.Team
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NPlusOneCollectionFetchJoinQueryTest : JpaTestBase() {

    @Test
    fun queryWithFetchJoin() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Team::class.java)
            val t = cq.from(Team::class.java)
            t.fetch<Team, Player>("players")
            cq.select(t)

            val query = em.createQuery(cq)
            // select team0_.id as id1_7_0_, players1_.player_id as player_i1_6_1_, team0_.name as name2_7_0_,
            // players1_.name as name2_6_1_, players1_.salary as salary3_6_1_, players1_.team_id as team_id4_6_1_,
            // players1_.team_id as team_id4_6_0__, players1_.player_id as player_i1_6_0__ from Team team0_
            // inner join Player players1_ on team0_.id=players1_.team_id
            val teams = query.resultList

            assertThat(teams, hasSize(6))
            Assertions.assertTrue(teams[0] == teams[1])

            teams[0].players // 추가 로딩하지 않음
        } finally {
            em.close()
        }
    }

}
