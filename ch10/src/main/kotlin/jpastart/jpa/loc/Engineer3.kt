package jpastart.jpa.loc

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "engineer3")
class Engineer3(
    @Id
    val id: String,
    val name: String
)
