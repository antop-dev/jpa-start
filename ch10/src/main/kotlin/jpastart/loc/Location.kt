package jpastart.loc

import javax.persistence.*

@Entity
class Location(
    @Id
    val id: String,
    val name: String
) {
    @OneToMany
    @JoinTable(
        name = "loc_eng",
        joinColumns = [JoinColumn(name = "location_id")],
        inverseJoinColumns = [JoinColumn(name = "engineer_id")]
    )
    @OrderColumn(name = "list_idx")
    val engineers: MutableList<Engineer> = mutableListOf()

}
