package jpastart.issue

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.core.IsInstanceOf
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class IssueTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val issue = Issue(LocalDateTime.now(), "홍길동", "010-1234-5678", "그냥 이슈")
            // insert into issue (closed, content, customer_cp, customer_name, issue_date, issue_type) values
            // (0, '그냥 이슈', '010-1234-5678', '홍길동', '01/26/2021 03:13:16.575', 'IS')
            em.persist(issue)

            assertThat(issue.id, greaterThan(0L))

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
            // select issue0_.id as id2_0_0_, issue0_.closed as closed3_0_0_, issue0_.content as content4_0_0_,
            // issue0_.customer_cp as customer5_0_0_, issue0_.customer_name as customer6_0_0_, issue0_.issue_date
            // as issue_da7_0_0_, issue0_.assignee_emp_id as assignee8_0_0_, issue0_.schedule_date as schedule9_0_0_,
            // issue0_.response as respons10_0_0_, issue0_.issue_type as issue_ty1_0_0_ from issue issue0_
            // where issue0_.id=1
            val issue = em.find(Issue::class.java, 1L)
            assertThat(issue, IsInstanceOf(Issue::class.java))

            val visitRes = em.find(Issue::class.java, 2L)
            assertThat(visitRes, IsInstanceOf(Issue::class.java))
            assertThat(visitRes, IsInstanceOf(VisitReservation::class.java))

            val appeal = em.find(Issue::class.java, 3L)
            assertThat(visitRes, IsInstanceOf(Issue::class.java))
            assertThat(appeal, IsInstanceOf(Appeal::class.java))
        } finally {
            em.close()
        }
    }
}
