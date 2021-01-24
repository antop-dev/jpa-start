package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.guide.Itinerary
import jpastart.team.Team
import org.junit.jupiter.api.Test

class CollectionFuncTest : JpaTestBase() {

    @Test
    fun size() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Team::class.java)
            val t = cq.from(Team::class.java)

            cq.select(t).where(cb.gt(cb.size(t.get("players")), 1))

            val query = em.createQuery(cq)
            // select team0_.id as id1_7_, team0_.name as name2_7_ from Team team0_ where (select count(players1_.team_id)
            // from Player players1_ where team0_.id = players1_.team_id)>1
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun index() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select i from Itinerary i join i.sites s where s = ?1 and index(s) = 0",
                Itinerary::class.java
            ).apply {
                setParameter(1, "부소산성")
            }
            // select itinerary0_.id as id1_3_, itinerary0_.description as descript2_3_, itinerary0_.name
            // as name3_3_ from Itinerary itinerary0_ inner join itinerary_site sites1_ on itinerary0_.id=sites1_.itinerary_id
            // where sites1_.site='부소산성' and sites1_.list_idx=0
            query.resultList
        } finally {
            em.close()
        }
    }


}
