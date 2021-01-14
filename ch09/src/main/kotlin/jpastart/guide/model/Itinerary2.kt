package jpastart.guide.model

import javax.persistence.*

@Entity(name = "itinerary")
class Itinerary2(
    val name: String,
    val description: String,
    @ElementCollection
    @CollectionTable(
        name = "itinerary_site",
        joinColumns = [JoinColumn(name = "itinerary_id")]
    )
    @OrderColumn(name = "list_idx")
    @Column(name = "site")
    var sites: MutableList<SiteInfo> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun changeSites(sites: MutableList<SiteInfo>) {
        this.sites = sites
    }

}
