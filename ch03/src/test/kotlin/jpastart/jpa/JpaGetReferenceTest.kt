package jpastart.jpa

import jpastart.JpaTestBase
import jpastart.reserve.model.Hotel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.hibernate.LazyInitializationException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import javax.persistence.EntityNotFoundException

class JpaGetReferenceTest : JpaTestBase() {

    @Test
    fun accessInTx() {
        val em = EMF.createEntityManager()

        try {
            val hotel = em.getReference(Hotel::class.java, "H100-01")
            // 프록시 객체를 리턴하며, 이 시점에서 쿼리를 실행하지 않음
            assertThat(hotel, notNullValue())
            // 최초로 데이터에 접근할 때 쿼리를 실행한다.
            val name = hotel.name
            assertThat(name, `is`("최고 호텔"))
        } catch (e: Exception) {
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun accessOutTx() {
        val em = EMF.createEntityManager()

        val hotel: Hotel
        try {
            hotel = em.getReference(Hotel::class.java, "H100-01")
        } catch (e: Exception) {
            throw e
        } finally {
            em.close()
        }

        //
        assertThrows(LazyInitializationException::class.java) { hotel.name }
    }

    @Test
    fun notFound() {
        val em = EMF.createEntityManager()

        try {
            val hotel = em.getReference(Hotel::class.java, "NOT_FOUND")
            assertThrows(EntityNotFoundException::class.java) { hotel.name }
        } catch (e: Exception) {
            throw e
        } finally {
            em.close()
        }

    }
}
