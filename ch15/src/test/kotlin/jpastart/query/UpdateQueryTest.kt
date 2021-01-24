package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.Grade
import jpastart.reserve.Hotel
import jpastart.reserve.Hotel_
import org.junit.jupiter.api.Test

class UpdateQueryTest : JpaTestBase() {

    @Test
    fun jpql() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val query = em.createQuery("update Hotel h set h.name = :newName where h.name = :oldName").apply {
                setParameter("newName", "베스트웨스턴 구로")
                setParameter("oldName", "베스트웨스턴 구로호텔")
            }
            // update Hotel set name='베스트웨스턴 구로' where name='베스트웨스턴 구로호텔'
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
            val cu = cb.createCriteriaUpdate(Hotel::class.java)
            val h = cu.from(Hotel::class.java)
            cu.set(h.get(Hotel_.name), "베스트웨스턴 구로")
            cu.set(h.get(Hotel_.grade), Grade.STAR3)
            cu.where(cb.equal(h.get(Hotel_.name), "베스트웨스턴 구로호텔"))

            val query = em.createQuery(cu)
            // update Hotel set name='베스트웨스턴 구로', grade='STAR3' where name='베스트웨스턴 구로호텔'
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
