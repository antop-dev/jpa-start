package jpastart.jpa.team

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import javax.persistence.PersistenceException

class TeamTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val p3 = em.find(Player::class.java, "P3")
            val p4 = Player("P4", "선수4")
            val p5 = Player("P45", "선수5")
            em.persist(p4)
            em.persist(p5)

            val t3 = Team("T3", "팀3")
            assertThat(t3.players, hasSize(0))

            // mappedBy 가 설정된 Player에 team 값만 바꿔도 쿼리는 적용되지만
            // 양방향(단방향+단방향)을 설정하기 위해서 서로 추가해야 한다.
            p3.team = t3
            t3.players += p3

            p4.team = t3
            t3.players += p4

            p5.team = t3
            t3.players += p5

            em.persist(t3)

            tx.commit()

            assertThat(t3.players, hasSize(3))
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun notPersist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val p = Player("P4", "선수4")
            // 관리 상태의 엔티티이어야 한다.
            // em.persist(p)

            val team = Team("T4", "팀3")
            p.team = team

            assertThrows(PersistenceException::class.java) {
                em.persist(team)
            }

            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun removePlayer() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val team = em.find(Team::class.java, "T1")
            team.players.removeIf { it.id == "P2" }

            // update Player set team_id=null where team_id='T1' and player_id='P2'
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun removePlayers() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val team = em.find(Team::class.java, "T1")
            team.players.clear()

            // update Player set team_id=null where team_id='T1'
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
