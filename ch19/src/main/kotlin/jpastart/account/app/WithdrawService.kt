package jpastart.account.app

import jpastart.account.model.Account
import jpastart.jpa.EMF
import mu.KotlinLogging
import javax.persistence.LockModeType

class WithdrawService {
    private val log = KotlinLogging.logger {}

    fun withdraw(accountNum: String, value: Int): Int {
        val em = EMF.currentEntityManager()
        try {
            em.transaction.begin()

            val hints = mapOf("javax.persistence.lock.time" to 1_000)
            val account = em.find(Account::class.java, accountNum, LockModeType.PESSIMISTIC_WRITE, hints)
            log.info { "acquire lock" }
            if (account == null) throw AccountNotFoundException()
            Thread.sleep(200) // 잠금 테스트를 위한 슬립 처리
            account.withdraw(value)
            em.transaction.commit()
            log.info { "transaction committed" }
            return account.balance
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
