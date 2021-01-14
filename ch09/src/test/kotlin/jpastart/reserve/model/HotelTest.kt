package jpastart.reserve.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

class HotelTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val hotel = Hotel("H-GURO", "구로호텔")
            hotel.addProperty("추가1", "추가 정보")
            hotel.addProperty("추가2", "추가 정보2")
            em.persist(hotel)

            // insert into Hotel (name, id) values ('구로호텔', 'H-GURO')
            // insert into hotel_property (hotel_id, prop_name, prop_value) values ('H-GURO', '추가1', '추가 정보')
            // insert into hotel_property (hotel_id, prop_name, prop_value) values ('H-GURO', '추가2', '추가 정보2')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun changeProperty() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val hotel = em.find(Hotel::class.java, "H100-01")
            val properties = hotel.properties
            properties["NOISE"] = "조용"
            properties -= "VIEW"
            properties += "AIR" to "좋음"

            // delete from hotel_property where hotel_id='H100-01' and prop_name='VIEW'
            // update hotel_property set prop_value='조용' where hotel_id='H100-01' and prop_name='NOISE'
            // insert into hotel_property (hotel_id, prop_name, prop_value) values ('H100-01', 'AIR', '좋음')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun removeProperties() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val hotel = em.find(Hotel::class.java, "H100-01")
            hotel.properties.clear()

            // delete from hotel_property where hotel_id='H100-01'
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
