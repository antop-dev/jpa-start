package jpastart.query

import jpastart.JpaTestBase
import jpastart.guide.BestSightSummary
import jpastart.guide.Sight
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class SubselectQueryTest : JpaTestBase() {

    @Test
    fun subselect() {
        val em = EMF.createEntityManager()

        try {
            em.transaction.begin()
            // select sight0_.id as id1_2_0_, sight0_.name as name2_2_0_ from sight sight0_ where sight0_.id=1
            val sight = em.find(Sight::class.java, 1L)
            sight.name = "새이름"

            // @Synchronize 에 의해서 조회 하기 전에 업데이트가 수행됨
            // update sight set name='새이름' where id=1

            // select bestsights0_.id as id1_0_, bestsights0_.hoursOperation as hoursope2_0_, bestsights0_.name as name3_0_
            // from ( select s.id, s.name, d.hours_op as hoursOperation from sight s, sight_detail  d where s.id = d.sight_id ) bestsights0_
            // where bestsights0_.id=1
            val query =
                em.createQuery("select s from BestSightSummary s where s.id = :id", BestSightSummary::class.java)
            query.setParameter("id", 1L)

            val summary = query.singleResult
            // 수정이 반영되어 있다.
            assertThat(summary.name, `is`("새이름"))

            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun subQuery() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select s from BestSightSummary s where s.id = :id").apply {
                setParameter("id", 1L)
            }

            // from 절의 서브쿼리로 실행된다.
            // select bestsights0_.id as id1_0_, bestsights0_.hoursOperation as hoursope2_0_, bestsights0_.name as name3_0_
            // from ( select s.id, s.name, d.hours_op as hoursOperation from sight s, sight_detail d where s.id = d.sight_id ) bestsights0_
            // where bestsights0_.id=1
            query.resultList
        } finally {
            em.close()
        }
    }


}
