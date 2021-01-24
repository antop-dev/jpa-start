package jpastart.guide

import org.hibernate.annotations.Immutable
import org.hibernate.annotations.Subselect
import org.hibernate.annotations.Synchronize
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Immutable
@Subselect(
    "select s.id, s.name, d.hours_op as hoursOperation" +
            " from sight s, sight_detail d" +
            " where s.id = d.sight_id"
)
@Synchronize("sight", "sight_detail")
class BestSightSummary {
    @Id
    val id: Long = 0

    val name: String = ""

    val hoursOperation: String = ""

    override fun toString(): String {
        return "BestSightSummary(id=$id, name='$name', hoursOperation='$hoursOperation')"
    }

}
