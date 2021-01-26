package jpastart.issue

import java.time.LocalDateTime
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("AP")
class Appeal(issueDate: LocalDateTime, customerName: String, customerCp: String, content: String) :
    Issue(issueDate, customerName, customerCp, content) {
    var response: String? = null
}
