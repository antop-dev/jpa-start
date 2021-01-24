package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.common.IdName
import jpastart.jpa.team.Player
import org.junit.jupiter.api.Test

class SelectColumnsTest : JpaTestBase() {

    @Test
    fun selectColumn() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Array<Any>::class.java)
            val p = cq.from(Player::class.java)
            cq.multiselect(p.get<String>("id"), p.get<String>("name"), p.get<Int>("salary"))

            val query = em.createQuery(cq)
            val rows = query.resultList
            for (row in rows) {
                println("id = ${row[0]}, name = ${row[1]}, salary = ${row[2]}")
            }
        } finally {
            em.close()
        }
    }

    @Test
    fun selectByClass() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(IdName::class.java)
            val p = cq.from(Player::class.java)
            cq.select(cb.construct(IdName::class.java, p.get<String>("id"), p.get<String>("name")))

            val query = em.createQuery(cq)
            val rows = query.resultList
            for (row in rows) {
                println("id = ${row.id}, name = ${row.name}")
            }
        } finally {
            em.close()
        }
    }


}
