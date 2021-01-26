package jpastart.account.model

import javax.persistence.*

@Entity
@Table(name = "customer")
class Customer(
    @Id
    val id: String,
    @Column(name = "secret_code")
    var secretCode: String
) {
    fun changeSecretCode(newSecCode: String) {
        this.secretCode = newSecCode
    }

    @Version
    val ver: Int = 0
}
