package jpastart.guide.model

import javax.persistence.*

@Entity
class Sight(
    val name: String,

    @ElementCollection
    @CollectionTable(
        name = "sight_rec_item",
        joinColumns = [JoinColumn(name = "sight_id")]
    )
    @OrderBy("name asc")
    var recommendations: MutableSet<RecItem> = mutableSetOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
