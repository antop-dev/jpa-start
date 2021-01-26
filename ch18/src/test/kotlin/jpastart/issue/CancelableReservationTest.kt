package jpastart.issue

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CancelableReservationTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val cr = CancelableReservation(LocalDateTime.now(), "홍길동", "010-1234-5678", "그냥 이슈").apply {
                assigneeEngineerId = "ENG06"
                scheduleDate = LocalDateTime.now().plusDays(3)
            }


            em.persist(cr)

            assertThat(cr.id, Matchers.greaterThan(0L))

            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun findById() {
        val em = EMF.createEntityManager()
        try {
            // select visitreser0_.id as id2_0_0_, visitreser0_.closed as closed3_0_0_, visitreser0_.content
            // as content4_0_0_, visitreser0_.customer_cp as customer5_0_0_, visitreser0_.customer_name as
            // customer6_0_0_, visitreser0_.issue_date as issue_da7_0_0_, visitreser0_.assignee_emp_id as
            // assignee8_0_0_, visitreser0_.schedule_date as schedule9_0_0_ from issue visitreser0_ where
            // visitreser0_.id=2 and visitreser0_.issue_type='VR'
            // ↑ 검색 조건에 issue_type = 'VR' 추가됨
            val visitRes = em.find(VisitReservation::class.java, 2L)
            assertThat(visitRes, IsInstanceOf(Issue::class.java))
            assertThat(visitRes, IsInstanceOf(VisitReservation::class.java))
        } finally {
            em.close()
        }
    }

}
