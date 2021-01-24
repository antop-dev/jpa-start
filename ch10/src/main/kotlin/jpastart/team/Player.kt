package jpastart.team

import javax.persistence.*

@Entity
@Table(name = "player")
class Player(
    @Id
    @Column(name = "player_id")
    val id: String,
    val name: String
) {
    @ManyToOne
    @JoinColumn(name = "team_id")
    var team: Team? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
