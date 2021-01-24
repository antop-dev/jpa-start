package jpastart.loc

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

class LocationTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val loc = Location("LOC03", "방화동")
            val e4 = Engineer("ENG04", "이름4")
            val e5 = Engineer("ENG05", "이름5")
            val e6 = Engineer("ENG06", "이름6")
            loc.engineers += listOf(e4, e5, e6)

            em.persist(loc)

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun removeEngineer() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val loc = em.find(Location::class.java, "LOC01")
            loc.engineers.removeAt(0)

            // delete from loc_eng where location_id='LOC01' and list_idx=2
            // update loc_eng set engineer_id='ENG003' where location_id='LOC01' and list_idx=1
            // update loc_eng set engineer_id='ENG002' where location_id='LOC01' and list_idx=0
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
