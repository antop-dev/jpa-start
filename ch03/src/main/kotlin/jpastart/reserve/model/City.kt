package jpastart.reserve.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.TableGenerator

@Entity
class City(
    val name: String
) {
    @Id
    @TableGenerator(
        name = "idgen",
        table = "id_gen",
        pkColumnName = "entity",
        pkColumnValue = "city",
        valueColumnName = "next_id",
        initialValue = 0,
        allocationSize = 1
    )
    @GeneratedValue(generator = "idgen")
    var id: Long = 0
        private set
}
