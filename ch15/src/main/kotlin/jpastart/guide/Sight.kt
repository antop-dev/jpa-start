package jpastart.guide

import javax.persistence.*

@Entity
@Table(name = "sight")
class Sight(
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
