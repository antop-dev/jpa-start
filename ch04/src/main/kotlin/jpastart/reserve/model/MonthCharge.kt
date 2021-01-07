package jpastart.reserve.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "month_charge")
class MonthCharge(
    @Id
    val id: MonthChargeId,

    @Column(name = "charge_amt")
    val chargeAmount: Int,

    @Column(name = "unpay_amt")
    val unpayAmount: Int
)
