package jpastart.perf

import javax.persistence.*

@Entity
class Performance(
    @Id
    val id: String,
    val name: String
) {
    @ManyToMany
    @JoinTable(
        name = "perf_person",
        joinColumns = [JoinColumn(name = "performance_id")],
        inverseJoinColumns = [JoinColumn(name = "person_id")]
    )
    val cast: MutableSet<Person> = mutableSetOf()
}
