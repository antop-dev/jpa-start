package jpastart.reserve.model

import javax.persistence.*

@Entity
@Table(name = "room_info")
class Room(
    @Id
    val number: String,
    var name: String
) {
    @Column(name = "description")
    private val desc: String? = null

    @Column(name = "id", insertable = false, updatable = false)
    val dbId: Long = 0

    @Transient // 영속성 대상에서 제외
    private val timestamp = System.currentTimeMillis()

    fun changeName(newName: String) {
        name = newName
    }
}
