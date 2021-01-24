package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.reserve.User
import org.junit.jupiter.api.Test

class SimpleQueryTest : JpaTestBase() {

    @Test
    fun simpleTypedQuery() {
        val em = EMF.createEntityManager()
        try {
            val query = em.criteriaBuilder.run {
                createQuery(User::class.java).run {
                    from(User::class.java)
                    em.createQuery(this)
                }
            }
            // select user0_.email as email1_8_, user0_.create_date as create_d2_8_, user0_.name as name3_8_ from User user0_
            query.resultList
        } finally {
            em.close()
        }
    }

}
