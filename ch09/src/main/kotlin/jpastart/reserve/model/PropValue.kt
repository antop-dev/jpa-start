package jpastart.reserve.model

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class PropValue(
    @Column(name = "prop_value")
    val value: String,
    val type: String
)
