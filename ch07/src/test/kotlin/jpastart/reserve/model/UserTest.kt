package jpastart.reserve.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

class UserTest : JpaTestBase() {

    @Test
    fun findById() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val user = em.find(User::class.java, "madvirus@madvirus.net")
            val card = em.find(MembershipCard::class.java, "4040")
            user.issue(card)
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
