package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.reserve.User
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import javax.persistence.NoResultException
import javax.persistence.NonUniqueResultException

internal class ExactSingleRowTest : JpaTestBase() {

    @Test
    fun singleResult() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select count(p) from Player p", java.lang.Long::class.java)
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
            val query = em.createQuery("select u from User u where u.email = :email", User::class.java).apply {
                setParameter("email", "antop@kakao.com")
            }
            assertThrows(NoResultException::class.java) { query.singleResult }
        } finally {
            em.close()
        }
    }

    @Test
    fun notSingleResult() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select u from User u", User::class.java)
            assertThrows(NonUniqueResultException::class.java) { query.singleResult }
        } finally {
            em.close()
        }
    }

}
