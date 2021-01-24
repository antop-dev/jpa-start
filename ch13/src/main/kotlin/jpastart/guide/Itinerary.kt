package jpastart.guide

import javax.persistence.*

@Entity
class Itinerary(
    val name: String,
    val description: String,
    @ElementCollection
    @CollectionTable(
        name = "itinerary_site",
        joinColumns = [JoinColumn(name = "itinerary_id")]
    )
    @OrderColumn(name = "list_idx")
    @Column(name = "site")
    var sites: MutableList<String?> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun changeSites(sites: MutableList<String?>) {
        this.sites = sites
    }

}
