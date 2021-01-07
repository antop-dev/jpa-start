package jpastart.guide.model

import javax.persistence.*

@Entity
class City(
    val name: String,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "address.zipcode", column = Column(name = "city_zip")),
        AttributeOverride(name = "address.address1", column = Column(name = "city_addr1")),
        AttributeOverride(name = "address.address2", column = Column(name = "city_addr2")),
    )
    val contactInfo: ContactInfo
) {
    @Id
    @TableGenerator(
        name = "idgen",
        table = "id_gen",
        pkColumnName = "entity",
        pkColumnValue = "city",
        valueColumnName = "next_id",
        initialValue = 0,
        allocationSize = 1
    )
    @GeneratedValue(generator = "idgen")
    var id: Long = 0
        private set
}
