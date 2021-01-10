package jpastart.guide.model

import jpastart.reserve.model.User
import javax.persistence.*

@Entity(name = "user_best_sight")
class UserBestSight(
    @OneToOne
    @PrimaryKeyJoinColumn
    val user: User,
    val title: String,
    val description: String
) {
    @Id
    @Column(name = "email")
    val email: String = user.email
}
