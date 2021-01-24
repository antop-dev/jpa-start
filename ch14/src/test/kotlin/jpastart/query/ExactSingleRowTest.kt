package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.User
import jpastart.team.Player
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import javax.persistence.NoResultException
import javax.persistence.NonUniqueResultException

class ExactSingleRowTest : JpaTestBase() {

    @Test
    fun singleResult() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Long::class.java)
            val p = cq.from(Player::class.java)

            cq.select(cb.count(p))

            val query = em.createQuery(cq)
            // select count(player0_.player_id) as col_0_0_ from Player player0_
            val count = query.singleResult
            assertThat(count, `is`(6))
        } finally {
            em.close()
        }
    }

    @Test
    fun noResult() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(User::class.java)
            val p = cq.from(User::class.java)

            cq.select(p).where(cb.equal(p.get<String>("email"), "antop@kakao.com"))

            val query = em.createQuery(cq)
            assertThrows(NoResultException::class.java) {
                // select user0_.email as email1_8_, user0_.create_date as create_d2_8_, user0_.name as name3_8_
                // from User user0_ where user0_.email='antop@kakao.com'
                query.singleResult
            }
        } finally {
            em.close()
        }
    }

    @Test
    fun notSingleResult() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(User::class.java)
            val p = cq.from(User::class.java)

            val query = em.createQuery(cq.select(p))
            assertThrows(NonUniqueResultException::class.java) {
                // select user0_.email as email1_8_, user0_.create_date as create_d2_8_, user0_.name as name3_8_
                //from User user0_
                query.singleResult
            }
        } finally {
            em.close()
        }
    }

}
