package jpastart.issue

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.hamcrest.core.IsInstanceOf
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class AppealTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val appeal = Appeal(LocalDateTime.now(), "홍길동", "010-1234-5678", "그냥 이슈").apply {
                response = "응답하라"
            }

            // insert into issue (closed, content, customer_cp, customer_name, issue_date, response, issue_type)
            // values (0, '그냥 이슈', '010-1234-5678', '홍길동', '01/26/2021 03:16:51.089', '응답하라', 'AP')
            em.persist(appeal)

            assertThat(appeal.id, Matchers.greaterThan(0L))

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
            // select appeal0_.id as id2_0_0_, appeal0_.closed as closed3_0_0_, appeal0_.content as content4_0_0_,
            // appeal0_.customer_cp as customer5_0_0_, appeal0_.customer_name as customer6_0_0_, appeal0_.issue_date
            // as issue_da7_0_0_, appeal0_.response as respons10_0_0_ from issue appeal0_ where appeal0_.id=3
            // and appeal0_.issue_type='AP'
            // ↑ 검색 조건에 issue_type = 'AP' 추가됨
            val visitRes = em.find(Appeal::class.java, 3L)
            assertThat(visitRes, IsInstanceOf(Issue::class.java))
            assertThat(visitRes, IsInstanceOf(Appeal::class.java))
            assertThat(visitRes, not(IsInstanceOf(VisitReservation::class.java)))
        } finally {
            em.close()
        }
    }
}
