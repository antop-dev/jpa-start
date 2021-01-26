package jpastart.issue

import java.time.LocalDateTime
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("CR")
class CancelableReservation(issueDate: LocalDateTime, customerName: String, customerCp: String, content: String) :
    VisitReservation(issueDate, customerName, customerCp, content) {
    init {
        close()
    }
}
