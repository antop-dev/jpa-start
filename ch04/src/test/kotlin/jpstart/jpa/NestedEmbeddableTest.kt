package jpstart.jpa

import jpastart.common.model.Address
import jpastart.guide.model.ContactInfo
import jpastart.guide.model.City
import jpstart.JpaTestBase
import org.junit.jupiter.api.Test

class NestedEmbeddableTest : JpaTestBase() {

    @Test
    fun insert() {
        val em = jpastart.jpa.EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            val city = City(
                "서울",
                ContactInfo(
                    "02-120", "seoul@seoul.kr",
                    Address("04524", "서울특별시 중구 ", "세종대로 110")
                )
            )
            em.persist(city)
            tx.commit()
            // insert into City (city_addr1, city_addr2, city_zip, ct_email, ct_phone, name, id) values (?, ?, ?, ?, ?, ?, ?)
        } catch (ex: Exception) {
            tx.rollback()
            throw ex
        } finally {
            em.close()
        }
    }

}
