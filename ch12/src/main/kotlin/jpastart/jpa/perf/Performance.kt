package jpastart.jpa.perf

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Performance(
    @Id
    val id: String,
    val name: String
)
