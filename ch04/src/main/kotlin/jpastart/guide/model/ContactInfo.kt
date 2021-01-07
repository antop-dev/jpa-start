package jpastart.guide.model

import jpastart.common.model.Address
import javax.persistence.*

@Embeddable
class ContactInfo(
    @Column(name = "ct_phone")
    val phone: String,

    @Column(name = "ct_email")
    val email: String,

    // @Embeddable 중첩
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "zipcode", column = Column(name = "ct_zip")),
        AttributeOverride(name = "address1", column = Column(name = "ct_addr1")),
        AttributeOverride(name = "address2", column = Column(name = "ct_addr2"))
    )
    val address: Address
)
