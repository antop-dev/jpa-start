package jpastart.guide.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test

class RecItemTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val sight = Sight("광화문")
            sight.recommendations = mutableSetOf(
                RecItem("추천1", "타입1"),
                RecItem("추천1", "타입1") // equals
            )
            em.persist(sight)

            // insert into Sight (name) values ('광화문')
            // insert into sight_rec_item (sight_id, name, type) values (105, '추천1', '타입1')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun changeRecommendations() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val sight = em.find(Sight::class.java, 1L)
            sight.recommendations.remove(RecItem("근정전", "ARCH"))
            sight.recommendations.remove(RecItem("광화문", "WALL"))

            // 전부 삭제 후 다시 인서트 한다.
            // delete from sight_rec_item where sight_id=1
            // insert into sight_rec_item (sight_id, name, type) values (1, '사정전', 'ARCH')
            // insert into sight_rec_item (sight_id, name, type) values (1, '교태전', 'ARCH')
            // insert into sight_rec_item (sight_id, name, type) values (1, '경희루', 'PARTY')
            // insert into sight_rec_item (sight_id, name, type) values (1, '동궁', 'ARCH')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
