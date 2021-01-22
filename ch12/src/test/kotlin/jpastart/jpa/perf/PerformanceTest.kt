package jpastart.jpa.perf

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test

class PerformanceTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val perf = em.find(Performance::class.java, "PF002")
            val person = em.find(Person::class.java, "P05")
            val castMap = CastMap(perf, person)
            em.persist(castMap)

            // insert into perf_person (performance_id, person_id) values ('PF002', 'P05')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun remove() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val castMap = em.find(CastMap::class.java, CastMapId("PF002", "P03"))
            em.remove(castMap)

            // delete from perf_person where performance_id='PF002' and person_id='P03'
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun query() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val query = em.createQuery(
                "select cm.person from CastMap cm " +
                        "where cm.id.performanceId = :perfId " +
                        "order by cm.person.name desc", Person::class.java
            ).apply {
                setParameter("perfId", "PF001")
            }

            val personList = query.resultList

            assertThat(personList, hasSize(2))
            assertThat(personList[0].name, `is`("한성준"))

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
