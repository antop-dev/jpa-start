package jpastart.perf

import javax.persistence.*

@Entity
@Table(name = "perf_person")
class CastMap(
    @ManyToOne
    @JoinColumn(name = "performance_id", insertable = false, updatable = false)
    val performance: Performance,

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    val person: Person
) {
    @Id
    val id: CastMapId = CastMapId(performance.id, person.id)

}
