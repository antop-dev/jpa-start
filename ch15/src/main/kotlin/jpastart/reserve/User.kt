package jpastart.reserve

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User(
    @Id
    val email: String,

    val name: String,
    @Column(name = "create_date")
    val createTime: LocalDateTime
) {
    @ElementCollection
    @CollectionTable(name = "user_keyword", joinColumns = [JoinColumn(name = "user_email")])
    @Column(name = "keyword")
    val keywords: MutableSet<String> = mutableSetOf()
}
