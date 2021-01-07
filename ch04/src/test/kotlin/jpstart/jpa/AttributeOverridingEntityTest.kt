package jpstart.jpa

import jpastart.common.model.Address
import jpastart.jpa.EMF
import jpastart.guide.model.Sight
import jpstart.JpaTestBase
import org.junit.jupiter.api.Test

class AttributeOverridingEntityTest : JpaTestBase() {

    @Test
    fun overrideAttribute() {
        val em = EMF.createEntityManager()
        val transaction = em.transaction
        try {
            transaction.begin()
            val sight = Sight(
                "경복궁",
                Address("03045", "서울시 종로구", "세종로 1-1"),
                Address("03045", "Jongno-gu, Seoul", "161, Sajik-ro")
            )
            em.persist(sight)
            transaction.commit()
        } catch (ex: Exception) {
            transaction.rollback()
            throw ex
        } finally {
            em.close()
        }
    }

}
