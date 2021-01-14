package jpastart.guide.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test

class ItineraryTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val sites: MutableList<String?> = mutableListOf("경복궁", "청계천", "명동", "인사동")
            val itinerary = Itinerary("광화문-종로 인근", "설명", sites)
            em.persist(itinerary)

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun changeSites() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val itinerary = em.find(Itinerary::class.java, 1L)
            itinerary.changeSites(mutableListOf("정림사지", "궁암지", "부여박물관"))

            // delete from itinerary_site where itinerary_id=1
            // insert into itinerary_site (itinerary_id, list_idx, site) values (1, 0, '정림사지')
            // insert into itinerary_site (itinerary_id, list_idx, site) values (1, 1, '궁암지')
            // insert into itinerary_site (itinerary_id, list_idx, site) values (1, 2, '부여박물관')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun changeSite() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val itinerary = em.find(Itinerary::class.java, 1L)
            val sites = itinerary.sites
            sites[1] = "낙화산-금강 유람선"
            sites += "백제문화단지"

            // update itinerary_site set site='낙화산-금강 유람선' where itinerary_id=1 and list_idx=1
            // insert into itinerary_site (itinerary_id, list_idx, site) values (1, 4, '백제문화단지')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun removeSite() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val itinerary = em.find(Itinerary::class.java, 1L)
            val sites = itinerary.sites
            sites.removeAt(1)

            // delete from itinerary_site where itinerary_id=1 and list_idx=3
            // update itinerary_site set site='궁남지' where itinerary_id=1 and list_idx=2
            // update itinerary_site set site='정림사지' where itinerary_id=1 and list_idx=1
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun removeSites() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val itinerary = em.find(Itinerary::class.java, 1L)
            itinerary.sites.clear()

            // delete from itinerary_site where itinerary_id=1
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun `null`() {
        run {
            val em = EMF.createEntityManager()
            val tx = em.transaction
            try {
                tx.begin()

                val itinerary = em.find(Itinerary::class.java, 1L)
                val sites = itinerary.sites
                sites[1] = null

                // delete from itinerary_site where itinerary_id=1 and list_idx=1
                // 삭제만 하고 아래 순서를 위로 올리지 않는다.
                tx.commit()
            } catch (e: Exception) {
                tx.rollback()
                throw e
            } finally {
                em.close()
            }
        }

        run {
            val em = EMF.createEntityManager()
            val tx = em.transaction
            try {
                tx.begin()

                val itinerary = em.find(Itinerary::class.java, 1L)
                val sites = itinerary.sites

                assertThat(sites, hasSize(4))
                assertThat(sites[1], nullValue())

                tx.commit()
            } catch (e: Exception) {
                tx.rollback()
                throw e
            } finally {
                em.close()
            }
        }
    }

}
