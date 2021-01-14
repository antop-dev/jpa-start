package jpastart.guide.model

import javax.persistence.Embeddable

@Embeddable
data class SiteInfo(
    val site: String,
    val time: Int
)
