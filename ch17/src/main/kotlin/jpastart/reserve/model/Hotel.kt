package jpastart.reserve.model

import jpastart.common.model.Address
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
class Hotel(
    @Id
    val id: String,

    val name: String,

    @Enumerated(EnumType.STRING)
    val grade: Grade,

    val address: Address
)
