package jpastart.reserve.model

import javax.persistence.*

@Entity
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
