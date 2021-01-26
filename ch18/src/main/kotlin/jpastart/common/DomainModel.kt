package jpastart.common

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
class DomainModel {
    @Id
    var id = ""

    @Column(name = "crt_dtm")
    val creationDate: LocalDateTime = LocalDateTime.now()

    @Column(name = "crt_empid")
    var creationEmpId = ""

    @Column(name = "crt_ip")
    var creationIp = ""
}
