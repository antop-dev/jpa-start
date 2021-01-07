package jpastart.guide.model

import jpastart.common.model.Address
import javax.persistence.*

@Entity
@SecondaryTable(
    name = "sight_detail",
    pkJoinColumns = [PrimaryKeyJoinColumn(name = "sight_id", referencedColumnName = "id")]
)
class Sight(
    val name: String,

    @Embedded
    @AttributeOverride(name = "zipcode", column = Column(name = "kor_zipcode"))
    val korAddress: Address,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "zipcode", column = Column(name = "eng_zipcode")),
        AttributeOverride(name = "address1", column = Column(name = "eng_addr1")),
        AttributeOverride(name = "address2", column = Column(name = "eng_addr2"))
    )
    val engAddress: Address

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "hoursOfOperation", column = Column(name = "hours_op", table = "sight_detail")),
        AttributeOverride(name = "holidays", column = Column(table = "sight_detail")),
        AttributeOverride(name = "facilities", column = Column(table = "sight_detail"))
    )
    var detail: SightDetail? = null
}
