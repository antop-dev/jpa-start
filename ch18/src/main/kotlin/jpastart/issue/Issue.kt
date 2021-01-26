package jpastart.issue

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "issue")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "issue_type")
@DiscriminatorValue("IS")
class Issue(
    @Column(name = "issue_date")
    val issueDate: LocalDateTime,
    @Column(name = "customer_name")
    val customerName: String,
    @Column(name = "customer_cp")
    val customerCp: String,
    val content: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var closed = false

    final fun close() {
        this.closed = true
    }

    fun open() {
        this.closed = false
    }
}
