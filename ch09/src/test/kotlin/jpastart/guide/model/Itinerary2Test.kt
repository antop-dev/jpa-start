package jpastart.guide.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

class Itinerary2Test : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val sites = mutableListOf(
                SiteInfo("경복궁", 9),
                SiteInfo("청계천", 10),
                SiteInfo("명동", 11),
                SiteInfo("인사동", 12)
            )
            val itinerary = Itinerary2("광화문-종로 인근", "설명", sites)
            em.persist(itinerary)

            // insert into itinerary_site (itinerary_id, list_idx, site, time) values (3, 0, '경복궁', 9)
            // insert into itinerary_site (itinerary_id, list_idx, site, time) values (3, 1, '청계천', 10)
            // insert into itinerary_site (itinerary_id, list_idx, site, time) values (3, 2, '명동', 11)
            // insert into itinerary_site (itinerary_id, list_idx, site, time) values (3, 3, '인사동', 12)
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

            val itinerary = em.find(Itinerary2::class.java, 1L)
            itinerary.changeSites(
                mutableListOf(
                    SiteInfo("정림사지", 15),
                    SiteInfo("궁암지", 16),
                    SiteInfo("부여박물관", 17)
                )
            )

            // delete from itinerary_site where itinerary_id=1
            // insert into itinerary_site (itinerary_id, list_idx, site, time) values (1, 0, '정림사지', 15)
            // insert into itinerary_site (itinerary_id, list_idx, site, time) values (1, 1, '궁암지', 16)
            // insert into itinerary_site (itinerary_id, list_idx, site, time) values (1, 2, '부여박물관', 17)
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

            val itinerary = em.find(Itinerary2::class.java, 1L)
            val sites = itinerary.sites
            sites[1] = SiteInfo("낙화산-금강 유람선", 11)
            sites += SiteInfo("백제문화단지", 18)

            // update itinerary_site set site='낙화산-금강 유람선', time=11 where itinerary_id=1 and list_idx=1
            // insert into itinerary_site (itinerary_id, list_idx, site, time) values (1, 4, '백제문화단지', 18)
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

            val itinerary = em.find(Itinerary2::class.java, 1L)
            val sites = itinerary.sites
            sites.removeAt(1)

            // delete from itinerary_site where itinerary_id=1 and list_idx=3
            // update itinerary_site set site='궁남지', time=45 where itinerary_id=1 and list_idx=2
            // update itinerary_site set site='정림사지', time=60 where itinerary_id=1 and list_idx=1
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

            val itinerary = em.find(Itinerary2::class.java, 1L)
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

}
