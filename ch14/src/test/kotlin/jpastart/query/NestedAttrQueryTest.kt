package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.guide.UserBestSight
import jpastart.reserve.User
import org.junit.jupiter.api.Test

class NestedAttrQueryTest : JpaTestBase() {

    @Test
    fun nestedAttribute() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder

            val cq = cb.createQuery(UserBestSight::class.java)
            val root = cq.from(UserBestSight::class.java)
            cq.select(root).where(cb.equal(root.get<User>("user").get<String>("name"), "고길동"))

            val query = em.createQuery(cq)
            // select userbestsi0_.email as email1_9_, userbestsi0_.description as descript2_9_, userbestsi0_.title
            // as title3_9_ from user_best_sight userbestsi0_ cross join User user1_ where userbestsi0_.email=user1_.email
            // and user1_.name='고길동'
            query.resultList
        } finally {
            em.close()
        }
    }

}
