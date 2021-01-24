package jpastart.perf

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class CastMapId(
    @Column(name = "performance_id")
    val performanceId: String,
    @Column(name = "person_id")
    val personId: String
) : Serializable
