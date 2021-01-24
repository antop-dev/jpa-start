package jpastart.team

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Team(
    @Id
    val id: String,
    val name: String
) {

    @OneToMany(mappedBy = "team")
    val players: MutableSet<Player> = mutableSetOf()

}
