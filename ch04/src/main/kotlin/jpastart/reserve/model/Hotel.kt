package jpastart.reserve.model

import jpastart.common.model.Address
import javax.persistence.*

@Entity
class Hotel(
    @Id
    val id: String,

    val name: String,

    @Enumerated(EnumType.STRING)
    val grade: Grade,

    @Embedded
    var address: Address
) {
    fun changeAddress(newAddress: Address) {
        this.address = newAddress
    }
}
