package jpastart.account.app

import jpastart.account.model.Customer
import jpastart.jpa.EMF
import mu.KotlinLogging
import java.util.concurrent.ThreadLocalRandom


class ChangeSecretCodeService {
    private val log = KotlinLogging.logger { }

    fun changeSecretCode(id: String, newSecCode: String) {
        val em = EMF.currentEntityManager()
        try {
            em.transaction.begin()
            val customer: Customer = em.find(Customer::class.java, id) ?: throw CustomerNotFoundException()
            log.info { "delay id=$id, newSecCode=$newSecCode" }
            sleepRandom() // 잠금 테스트를 위한 슬립처리
            customer.changeSecretCode(newSecCode)
            em.transaction.commit()
        } catch (ex: Exception) {
            em.transaction.rollback()
            throw ex
        } finally {
            em.close()
        }
    }

    private fun sleepRandom() {
        Thread.sleep(ThreadLocalRandom.current().nextLong(3000) + 500)
    }
}
