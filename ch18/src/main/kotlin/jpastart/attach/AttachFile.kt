package jpastart.attach

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "attach_file")
@Inheritance(strategy = InheritanceType.JOINED)
class AttachFile(
    @Id
    val id: String,
    val name: String
) {
    @Column(name = "upload_date")
    val uploadDate: LocalDateTime = LocalDateTime.now()

}
