package jpastart.jpa.perf

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

class PerformanceTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val person = em.find(Person::class.java, "P05")

            val perf1 = em.find(Performance::class.java, "PF001")
            perf1.cast += person // 연관 소유한 쪽에 연관 적용
            person.perfs += perf1 // 양방향 연관이 코드에서도 동작하도록 연관 적용

            val perf2 = em.find(Performance::class.java, "PF002")
            perf2.cast += person
            person.perfs += perf2

            // insert into perf_person (performance_id, person_id) values ('PF001', 'P05')
            // insert into perf_person (performance_id, person_id) values ('PF002', 'P05')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }

    }
}
