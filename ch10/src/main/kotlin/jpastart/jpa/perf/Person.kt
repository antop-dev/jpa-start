package jpastart.jpa.perf

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
class Person(
    @Id
    val id: String,
    val name: String
) {
    @ManyToMany(mappedBy = "cast")
    val perfs: MutableSet<Performance> = mutableSetOf()
}
