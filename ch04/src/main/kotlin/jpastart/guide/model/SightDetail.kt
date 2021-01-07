package jpastart.guide.model

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class SightDetail(
    @Column(name = "hours_op")
    val hoursOfOperation: String,

    val holidays: String,

    val facilities: String
)
