package jpstart.jpa

import jpastart.jpa.EMF
import jpastart.reserve.model.Hotel
import jpstart.JpaTestBase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test

class JpaFindTest : JpaTestBase() {

    @Test
    fun findNotNullEmbeddedValue() {
        val hotel = findHotelById("H100-01")
        assertThat(hotel?.address, notNullValue())
    }

    @Test
    fun findNullEmbeddedValue() {
        val hotel = findHotelById("H100-02")
        assertThat(hotel?.address, nullValue())
    }

    private fun findHotelById(hotelId: String): Hotel? {
        val em = EMF.createEntityManager()
        try {
            return em.find(Hotel::class.java, hotelId)
        } finally {
            em.close()
        }
    }

}
