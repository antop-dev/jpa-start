package jpastart.jpa.guide

import jpastart.jpa.reserve.User
import javax.persistence.*

@Entity
@Table(name = "user_best_sight")
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
