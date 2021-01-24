package jpastart.perf

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Person(
    @Id
    val id: String,
    val name: String
)
