package jpastart.account.app

import jpastart.JpaTestBase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class AccountTest : JpaTestBase() {
    private val depositService = DepositService()
    private val withdrawService = WithdrawService()

    @Test
    fun persist() {
        // select account0_.account_num as account_1_0_0_, account0_.balance as balance2_0_0_ from account
        // account0_ where account0_.account_num='02-008' for update
        val balance = depositService.deposit("02-008", 500)
        assertThat(balance, `is`(1500))
    }

    @Test
    fun pessimistic() {
        val tx1 = Thread { depositService.deposit("02-008", 500) }
        val tx2 = Thread { withdrawService.withdraw("02-008", 300) }
        tx1.start()
        tx2.start()
        tx1.join()
        tx2.join()
    }

}
