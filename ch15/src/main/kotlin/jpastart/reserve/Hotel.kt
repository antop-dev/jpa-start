package jpastart.reserve

import javax.persistence.*

@Entity
@NamedNativeQuery(
    name = "Hotel.all",
    resultClass = Hotel::class,
    query = "select * from hotel order by id desc"
)
class Hotel(
    @Id
    val id: String,

    val name: String,

    @Enumerated(EnumType.STRING)
    val grade: Grade

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
