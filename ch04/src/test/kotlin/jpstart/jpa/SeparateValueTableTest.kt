package jpstart.jpa

import jpastart.common.model.Address
import jpastart.guide.model.SightDetail
import jpastart.jpa.EMF
import jpastart.guide.model.Sight
import jpstart.JpaTestBase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test


class SeparateValueTableTest : JpaTestBase() {

    @Test
    fun saveSight() {
        val sight = Sight(
            "경복궁",
            Address("03045", "서울시 종로구", "세종로 1-1"),
            Address("03045", "Jongno-gu, Seoul", "1-1, Sejong-ro")
        )
        sight.detail = SightDetail("09~17시", "매주 화요일", "안내 설명")

        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            em.persist(sight)
            assertThat(sight.id, notNullValue())
            assertThat(sight.detail?.holidays, `is`("매주 화요일"))
            sight.detail = null
            tx.commit()
        } catch (ex: Exception) {
            tx.rollback()
            throw ex
        } finally {
            em.close()
        }
    }

    @Test
    fun outerJoin() {
        val em = EMF.createEntityManager()
        try {
            val sight = em.find(Sight::class.java, 99L)
            assertThat(sight.detail, notNullValue())
        } finally {
            em.close()
        }
    }
}
