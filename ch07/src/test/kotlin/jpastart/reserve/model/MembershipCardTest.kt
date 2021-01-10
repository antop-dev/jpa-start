package jpastart.reserve.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hibernate.LazyInitializationException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MembershipCardTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val owner = em.find(User::class.java, "madvirus@madvirus.net")
            val card = MembershipCard("1234", owner, LocalDateTime.of(2024, 1, 31, 23, 59, 59))
            em.persist(card)
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun findByNumber() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val card = em.find(MembershipCard::class.java, "5678")
            val owner = card.owner
            assertThat(owner?.name, `is`("고길동"))
            em.persist(card)
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun assignTo() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val card = em.find(MembershipCard::class.java, "4040")
            val user = User("eric@antop@.org", "Eric", LocalDateTime.now())
            card.assignTo(user)
            em.persist(user)

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun cancelAssignment() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val card = em.find(MembershipCard::class.java, "5678")
            card.cancelAssignment()
            assertThat(card.owner, Matchers.nullValue())

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun noSession() {
        val card: MembershipCard

        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            card = em.find(MembershipCard::class.java, "5678")
            card.owner = User("a", "aa", LocalDateTime.now())
            tx.begin()
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }

        // org.hibernate.LazyInitializationException: could not initialize proxy [jpastart.reserve.model.User#gildong@dooly.net] - no Sessio
        assertThrows(LazyInitializationException::class.java) {
            card.owner?.name
        }
    }
}
