package jpastart.reserve.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

class Hotel2Test : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val hotel = Hotel2("H-GURO", "구로호텔")
            hotel.addProperty("추가1", PropValue("추가 정보", "추가 타입"))
            hotel.addProperty("추가2", PropValue("추가 정보2", "추가 타입2"))
            em.persist(hotel)

            // insert into Hotel (name, id) values ('구로호텔', 'H-GURO')
            // insert into hotel_property2 (hotel_id, prop_name, type, prop_value) values ('H-GURO', '추가1', '추가 타입', '추가 정보')
            // insert into hotel_property2 (hotel_id, prop_name, type, prop_value) values ('H-GURO', '추가2', '추가 타입2', '추가 정보2')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
