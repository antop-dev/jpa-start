package jpastart.item

import jpastart.common.DomainModel
import javax.persistence.*

@Entity
@Table(name = "category")
@AttributeOverrides(
    AttributeOverride(name = "creationIp", column = Column(name = "creation_ip")),
    AttributeOverride(name = "creationDate", column = Column(name = "crt_dtm")),
)
class Category(val name: String) : DomainModel()
