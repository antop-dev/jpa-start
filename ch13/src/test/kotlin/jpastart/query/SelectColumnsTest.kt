package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.common.IdName
import org.junit.jupiter.api.Test

internal class SelectColumnsTest : JpaTestBase() {

    @Test
    fun selectColumn() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select p.id, p.name, p.salary from Player p", Array<Any>::class.java)
            val rows = query.resultList
            rows.forEach { row ->
                val id = row[0] as String
                val name = row[1] as String
                val salary = row[2] as Int
                println("id = $id, name = $name, salary = $salary")
            }
        } finally {
            em.close()
        }
    }

    @Test
    fun selectByClass() {
        val em = EMF.createEntityManager()
        try {
            val query =
                em.createQuery("select new jpastart.jpa.common.IdName(p.id, p.name) from Player p", IdName::class.java)
            // select player0_.player_id as col_0_0_, player0_.name as col_1_0_ from Player player0_
            val rows = query.resultList
            rows.forEach { println(it) }
        } finally {
            em.close()
        }
    }


}
