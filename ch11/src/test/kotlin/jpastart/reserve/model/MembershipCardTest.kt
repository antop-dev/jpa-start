package jpastart.reserve.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MembershipCardTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val owner = User("jvm@javaworld.co", "JVM", LocalDateTime.now())
            // em.persist(owner)
            val card = MembershipCard("1234", owner, LocalDateTime.now().plusDays(10))
            em.persist(card)

            // insert into User (create_date, name, email) values ('01/21/2021 10:27:58.422', 'JVM', 'jvm@javaworld.co')
            // insert into membership_card (enabled, expiry_date, user_email, card_number) values (1, '01/31/2021 10:27:58.422', 'jvm@javaworld.co', '1234')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
