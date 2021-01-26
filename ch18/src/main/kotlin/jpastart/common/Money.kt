package jpastart.common

import java.math.BigDecimal

data class Money(
    val value: BigDecimal,
    val currency: String
) {
    override fun toString() = "$value$currency"
}
