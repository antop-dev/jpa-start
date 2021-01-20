package jpastart.jpa.loc

import javax.persistence.*

@Entity(name = "location3")
class Location3(
    @Id
    val id: String,
    val name: String
) {
    @OneToMany
    @JoinTable(
        name = "loc_eng3",
        joinColumns = [JoinColumn(name = "location_id")],
        inverseJoinColumns = [JoinColumn(name = "engineer_id")]
    )
    @MapKeyColumn(name = "map_key")
    val engineers: MutableMap<String, Engineer3> = mutableMapOf()

    val primaryCharge: Engineer3?
        get() = engineers["MAIN"]

    val secondaryCharge: Engineer3?
        get() = engineers["SUB"]

}
