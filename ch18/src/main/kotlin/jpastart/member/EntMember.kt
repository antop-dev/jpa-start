package jpastart.member

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "ent_member")
class EntMember(
    id: String,
    name: String,
    @Column(name = "comp_id")
    val companyId: String
) : Member(id, name)
