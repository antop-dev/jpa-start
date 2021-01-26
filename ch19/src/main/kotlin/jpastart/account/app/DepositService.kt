package jpastart.account.app

import jpastart.account.model.Account
import jpastart.jpa.EMF
import mu.KotlinLogging
import javax.persistence.LockModeType

class DepositService {
    private val log = KotlinLogging.logger { }

    fun deposit(accountNum: String, value: Int): Int {
        val em = EMF.currentEntityManager()
        try {
            em.transaction.begin()
            val account = em.find(Account::class.java, accountNum, LockModeType.PESSIMISTIC_WRITE)
            log.info { "acquire lock" }
            if (account == null) throw AccountNotFoundException()
            Thread.sleep(10_000) // 잠금 테스트를 위한 슬립 처리
            account.deposit(value)
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
