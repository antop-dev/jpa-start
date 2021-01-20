package jpastart.guide.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test

class SightTest : JpaTestBase() {

    @Test
    fun orderBy() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val sight = em.find(Sight::class.java, 1L)
            assertThat(sight.recommendations, hasSize(6))

            // select recommenda0_.sight_id as sight_id1_6_0_,
            //       recommenda0_.name as name2_6_0_,
            //       recommenda0_.type as type3_6_0_
            // from sight_rec_item recommenda0_
            // where recommenda0_.sight_id = 1
            // order by recommenda0_.name asc
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
