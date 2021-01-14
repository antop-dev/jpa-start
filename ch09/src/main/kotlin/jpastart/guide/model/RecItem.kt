package jpastart.guide.model

import javax.persistence.Embeddable

@Embeddable
data class RecItem(
    val name: String,
    val type: String
)
