package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.reserve.Hotel
import jpastart.team.Player
import jpastart.team.Team
import org.junit.jupiter.api.Test

internal class NamedQueryTest : JpaTestBase() {

    @Test
    fun useXmlNamedQuery1() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createNamedQuery("Hotel.noReview", Hotel::class.java)
            // select hotel0_.id as id1_0_, hotel0_.name as name2_0_ from Hotel hotel0_ where not (exists
            // (select review1_.id from hotel_review review1_ where review1_.hotel_id=hotel0_.id)) order by
            // hotel0_.name
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun useXmlNamedQuery2() {
        val em = EMF.createEntityManager()
        try {
            val player = em.find(Player::class.java, "P1")
            val query = em.createNamedQuery("Team.hasPlayer", Team::class.java).apply {
                setParameter("player", player)
            }
            // select player0_.player_id as player_i1_5_0_, player0_.name as name2_5_0_, player0_.salary as
            // salary3_5_0_, player0_.team_id as team_id4_5_0_, team1_.id as id1_6_1_, team1_.name as name2_6_1_
            // from Player player0_ left outer join Team team1_ on player0_.team_id=team1_.id where player0_.player_id='P1'
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun useJavaNamedQuery1() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createNamedQuery("Hotel.all", Hotel::class.java)
            // select hotel0_.id as id1_0_, hotel0_.name as name2_0_ from Hotel hotel0_
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun useJavaNamedQuery2() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createNamedQuery("Hotel.findById", Hotel::class.java).apply {
                setParameter("id", "H-001")
            }
            // select hotel0_.id as id1_0_, hotel0_.name as name2_0_ from Hotel hotel0_ where hotel0_.id='H-001'
            query.resultList
        } finally {
            em.close()
        }
    }

}
