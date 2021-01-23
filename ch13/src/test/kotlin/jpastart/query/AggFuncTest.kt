package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

internal class AggFuncTest : JpaTestBase() {

    @Test
    fun aggregate() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select count(p), avg(p.salary), max(p.salary), min(p.salary) from Player p",
                Array<Any>::class.java
            )
            // select count(player0_.player_id) as col_0_0_, avg(player0_.salary) as col_1_0_, max(player0_.salary)
            // as col_2_0_, min(player0_.salary) as col_3_0_ from Player player0_
            query.singleResult
        } finally {
            em.close()
        }
    }

}
