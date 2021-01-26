package jpastart.item

import jpastart.common.Money
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Item(
    @Id
    val id: String,
    val name: String,
    val price: Money
)
