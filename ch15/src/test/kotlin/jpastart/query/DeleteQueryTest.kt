package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.Hotel
import jpastart.reserve.Hotel_
import org.junit.jupiter.api.Test

class DeleteQueryTest : JpaTestBase() {

    @Test
    fun jpql() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val query = em.createQuery("delete Hotel h where h.name = :name").apply {
                setParameter("name", "베스트웨스턴 구로호텔")
            }
            // delete from Hotel where name='베스트웨스턴 구로호텔'
            query.executeUpdate()

            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun criteria() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val cb = em.criteriaBuilder
            val cu = cb.createCriteriaDelete(Hotel::class.java)
            val h = cu.from(Hotel::class.java)
            cu.where(cb.equal(h.get(Hotel_.name), "베스트웨스턴 구로호텔"))

            val query = em.createQuery(cu)
            // delete from Hotel where name='베스트웨스턴 구로호텔'
            query.executeUpdate()

            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
