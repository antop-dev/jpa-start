package jpastart.issue

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("VR")
class VisitReservation(issueDate: LocalDateTime, customerName: String, customerCp: String, content: String) :
    Issue(issueDate, customerName, customerCp, content) {
    @Column(name = "assignee_emp_id")
    var assigneeEngineerId: String? = null

    @Column(name = "schedule_date")
    var scheduleDate: LocalDateTime? = null
}
