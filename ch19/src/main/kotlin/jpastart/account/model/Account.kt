package jpastart.account.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "account")
class Account(
    @Id
    @Column(name = "account_num")
    val accountNum: String
) {
    var balance: Int = 0

    fun deposit(num: Int) {
        balance += num
    }

    fun withdraw(num: Int) {
        if (balance < num) throw RuntimeException("잔액 부족")
        balance -= num
    }
}
