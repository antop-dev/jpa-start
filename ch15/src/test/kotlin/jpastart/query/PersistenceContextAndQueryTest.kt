package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.Hotel
import jpastart.reserve.Hotel_
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import javax.persistence.EntityNotFoundException

class PersistenceContextAndQueryTest : JpaTestBase() {

    @Test
    fun getThenUpdate() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val hotel = em.find(Hotel::class.java, "H100-01")
            assertThat(hotel.name, `is`("베스트웨스턴 구로호텔"))

            val query = em.createQuery("update Hotel h set h.name = :newName where h.id = :id").apply {
                setParameter("newName", "베스트웨스턴 구로")
                setParameter("id", "H100-01")
            }
            // update Hotel set name='베스트웨스턴 구로' where id='H100-01'
            query.executeUpdate()

            assertThat(hotel.name, `is`("베스트웨스턴 구로호텔"))
            // select hotel0_.id as id1_0_0_, hotel0_.grade as grade2_0_0_, hotel0_.name as name3_0_0_
            // from Hotel hotel0_ where hotel0_.id='H100-01'
            em.refresh(hotel)
            assertThat(hotel.name, `is`("베스트웨스턴 구로"))

            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun getThenDelete() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val hotel = em.find(Hotel::class.java, "H100-01")

            val cb = em.criteriaBuilder
            val cu = cb.createCriteriaDelete(Hotel::class.java)
            val h = cu.from(Hotel::class.java)
            cu.where(cb.equal(h.get(Hotel_.id), "H100-01"))

            val query = em.createQuery(cu)
            // delete from Hotel where id='H100-01'
            query.executeUpdate()

            assertThrows(EntityNotFoundException::class.java) {
                // select hotel0_.id as id1_0_0_, hotel0_.grade as grade2_0_0_, hotel0_.name as name3_0_0_
                // from Hotel hotel0_ where hotel0_.id='H100-01'
                em.refresh(hotel)
            }

            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }

}
