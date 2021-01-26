package jpastart.item

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

class CategoryTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val category = Category("카테-101").apply {
                id = "C101"
                creationEmpId = "E001"
                creationIp = "10.20.30.40"
            }

            em.persist(category)
            // insert into category (crt_dtm, crt_empid, creation_ip, name, id) values
            // ('01/26/2021 09:48:50.387', 'E001', '10.20.30.40', '카테-101', 'C101')
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
