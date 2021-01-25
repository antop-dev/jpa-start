package jpastart.common.model

import javax.persistence.Embeddable

@Embeddable
data class Address(
    val zipcode: String,
    val address1: String,
    val address2: String
)
