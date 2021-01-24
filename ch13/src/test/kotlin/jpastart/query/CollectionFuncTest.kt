package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.guide.Itinerary
import jpastart.team.Team
import org.junit.jupiter.api.Test

internal class CollectionFuncTest : JpaTestBase() {

    @Test
    fun size() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select t from Team t where size(t.players) > 1", Team::class.java)
            // select team0_.id as id1_4_, team0_.name as name2_4_ from Team team0_ where (select count(players1_.team_id)
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
