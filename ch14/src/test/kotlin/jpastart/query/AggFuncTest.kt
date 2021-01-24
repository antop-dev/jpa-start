package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.team.Player
import org.junit.jupiter.api.Test

class AggFuncTest : JpaTestBase() {

    @Test
    fun aggregate() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Array<Any>::class.java)
            val p = cq.from(Player::class.java)

            val salary = p.get<Double>("salary")
            cq.multiselect(cb.count(p), cb.sum(salary), cb.avg(salary), cb.min(salary))

            val query = em.createQuery(cq)
            // select count(player0_.player_id) as col_0_0_, sum(player0_.salary) as col_1_0_, avg(player0_.salary)
            // as col_2_0_, min(player0_.salary) as col_3_0_ from Player player0_
            query.singleResult
        } finally {
            em.close()
        }
    }

}
