package jpastart.issue

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.core.IsInstanceOf
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class VisitReservationTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val visitRes = VisitReservation(LocalDateTime.now(), "홍길동", "010-1234-5678", "그냥 이슈").apply {
                assigneeEngineerId = "ENG02"
                scheduleDate = LocalDateTime.now().plusDays(3)
            }

            // insert into issue (closed, content, customer_cp, customer_name, issue_date, assignee_emp_id,
            // schedule_date, issue_type) values (0, '그냥 이슈', '010-1234-5678', '홍길동', '01/26/2021 03:15:10.849',
            // 'ENG02', '01/29/2021 03:15:10.849', 'VR')
            em.persist(visitRes)

            assertThat(visitRes.id, greaterThan(0L))

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
