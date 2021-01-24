package jpastart.reserve

import javax.persistence.*

@Entity
@NamedQueries(
    NamedQuery(name = "Hotel.all", query = "select h from Hotel h"),
    NamedQuery(name = "Hotel.findById", query = "select h from Hotel h where h.id = :id")
)
class Hotel(
    @Id
    val id: String,

    val name: String,

    ) {
    @ElementCollection
    @CollectionTable(
        name = "hotel_property",
        joinColumns = [JoinColumn(name = "hotel_id")]
    )
    @MapKeyColumn(name = "prop_name")
    @Column(name = "prop_value")
    val properties: MutableMap<String, String> = mutableMapOf()

    fun addProperty(name: String, value: String) {
        properties[name] = value
    }

}
