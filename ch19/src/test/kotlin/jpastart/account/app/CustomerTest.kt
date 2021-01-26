package jpastart.account.app

import jpastart.JpaTestBase
import jpastart.account.model.Customer
import jpastart.jpa.EMF.Companion.createEntityManager
import org.junit.jupiter.api.Test


class CustomerTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = createEntityManager()
        try {
            em.transaction.begin()
            em.persist(Customer("heedong", "9878"))
            em.transaction.commit()
        } catch (ex: Exception) {
            em.transaction.rollback()
            throw ex
        } finally {
            em.close()
        }
    }

    @Test
    fun optimistic() {
        val secCodeService = ChangeSecretCodeService()
        // 둘중 늦게 업데이트 하는 곳에서 javax.persistence.OptimisticLockException 발생
        val tx1 = Thread { secCodeService.changeSecretCode("gildong", "9876") }
        val tx2 = Thread { secCodeService.changeSecretCode("gildong", "0000") }
        tx1.start()
        tx2.start()
        tx1.join()
        tx2.join()
    }

}
