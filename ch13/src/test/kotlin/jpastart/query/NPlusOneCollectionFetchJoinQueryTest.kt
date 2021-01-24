package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.team.Team
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class NPlusOneCollectionFetchJoinQueryTest : JpaTestBase() {

    @Test
    fun queryWithFetchJoin() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select t from Team t join fetch t.players p", Team::class.java)
            val teams = query.resultList

            assertThat(teams, hasSize(6))
            Assertions.assertTrue(teams[0] == teams[1])

            teams[0].players // 추가 로딩하지 않음
        } finally {
            em.close()
        }
    }

    @Test
    fun queryWithFetchJoinAndMaxResults() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select t from Team t join fetch t.players p", Team::class.java).apply {
                firstResult = 1
                maxResults = 1
            }
            // 메모리에 올린 후 중복 제거 후 페이징을 한다.
            val teams = query.resultList
            assertThat(teams[0].id, `is`("T2"))
        } finally {
            em.close()
        }
    }


}
