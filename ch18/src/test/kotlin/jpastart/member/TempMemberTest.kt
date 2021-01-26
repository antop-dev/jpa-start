package jpastart.member

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class TempMemberTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val member = TempMember("e002", "임시고객", "TMP001").apply {
                expireDate = LocalDateTime.now().plusMonths(1)
            }

            em.persist(member)

            // insert into temp_member (name, email, expire_date, id) values ('임시고객', 'TMP001', '02/26/2021 09:51:35.921', 'e002')
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
