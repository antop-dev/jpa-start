package jpstart.jpa

import jpastart.reserve.model.MonthCharge
import jpastart.reserve.model.MonthChargeId
import jpastart.jpa.EMF
import jpstart.JpaTestBase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test

class CompositeKeyTest : JpaTestBase() {
    @Test
    fun find() {
        val em = EMF.createEntityManager()
        try {
            val monthCharge = em.find(MonthCharge::class.java, MonthChargeId("H100-01", "201608"))
            assertThat(monthCharge, notNullValue())
            assertThat(monthCharge.id.hotelId, `is`("H100-01"))
            assertThat(monthCharge.id.yearMonth, `is`("201608"))
            assertThat(monthCharge.chargeAmount, `is`(1000))
            assertThat(monthCharge.unpayAmount, `is`(0))
        } finally {
            em.close()
        }
    }

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val id = MonthChargeId("H101-01", "201607")
            val monthCharge = MonthCharge(id, 30000, 10000)
            em.persist(monthCharge)
            tx.commit()
        } catch (ex: Exception) {
            tx.rollback()
            throw ex
        } finally {
            em.close()
        }
    }

}
