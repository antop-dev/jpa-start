package jpastart.jpa.loc

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Engineer(
    @Id
    val id: String,
    val name: String
)
