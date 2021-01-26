package jpastart.member

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "personal_member")
class PersonalMember(id: String, name: String, val email: String) : Member(id, name)
