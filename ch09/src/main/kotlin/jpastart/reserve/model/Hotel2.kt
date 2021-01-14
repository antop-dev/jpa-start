package jpastart.reserve.model

import javax.persistence.*

@Entity(name = "hotel")
class Hotel2(
    @Id
    val id: String,

    val name: String,

    ) {
    @ElementCollection
    @CollectionTable(
        name = "hotel_property2",
        joinColumns = [JoinColumn(name = "hotel_id")]
    )
    @MapKeyColumn(name = "prop_name")
    val properties: MutableMap<String, PropValue> = mutableMapOf()

    fun addProperty(name: String, prop: PropValue) {
        properties[name] = prop
    }

}
