package jpastart.loc

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class Location3Test : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val loc = Location3("LOC03", "방화동")
            val e4 = Engineer3("ENG04", "이름4")
            val e5 = Engineer3("ENG05", "이름5")
            val e6 = Engineer3("ENG06", "이름6")

            loc.engineers["MAIN"] = e4
            loc.engineers["SUB1"] = e5
            loc.engineers["SUB2"] = e6

            em.persist(loc)

            // insert into location3 (name, id) values ('방화동', 'LOC03')
            // insert into loc_eng3 (location_id, map_key, engineer_id) values ('LOC03', 'MAIN', 'ENG04')
            // insert into loc_eng3 (location_id, map_key, engineer_id) values ('LOC03', 'SUB1', 'ENG05')
            // insert into loc_eng3 (location_id, map_key, engineer_id) values ('LOC03', 'SUB2', 'ENG06')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun find() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val loc = em.find(Location3::class.java, "LOC01")

            assertThat(loc.primaryCharge?.id, `is`("ENG001"))
            assertThat(loc.primaryCharge?.name, `is`("최고수"))
            assertThat(loc.secondaryCharge?.id, `is`("ENG002"))
            assertThat(loc.secondaryCharge?.name, `is`("중수리"))

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
