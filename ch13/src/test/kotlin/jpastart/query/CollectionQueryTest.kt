package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.reserve.Hotel
import jpastart.jpa.reserve.User
import jpastart.jpa.team.Player
import jpastart.jpa.team.Team
import org.junit.jupiter.api.Test

internal class CollectionQueryTest : JpaTestBase() {

    @Test
    fun playerInTeam() {
        val em = EMF.createEntityManager()
        try {
            val player = em.find(Player::class.java, "P1")
            val query = em.createQuery(
                "select t from Team t where :player member of t.players order by t.name",
                Team::class.java
            )
            query.setParameter("player", player)
            // select player0_.player_id as player_i1_0_0_, player0_.name as name2_0_0_, player0_.salary as
            // salary3_0_0_, player0_.team_id as team_id4_0_0_, team1_.id as id1_1_1_, team1_.name as name2_1_1_
            // from Player player0_ left outer join Team team1_ on player0_.team_id=team1_.id where player0_.player_id='P1'
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun memberInElementCollection() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select u from User u where :keyword member of u.keywords order by u.name",
                User::class.java
            )
            query.setParameter("keyword", "부여")
            // select user0_.email as email1_2_, user0_.create_date as create_d2_2_, user0_.name as name3_2_
            // from User user0_ where '부여' in (select keywords1_.keyword from user_keyword keywords1_ where
            // user0_.email=keywords1_.user_email) order by user0_.name
            query.resultList
        } finally {
            em.close()
        }
    }


    @Test
    fun empty() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select t from Team t where t.players is empty", Team::class.java)
            // select team0_.id as id1_1_, team0_.name as name2_1_ from Team team0_ where not (exists (select
            // players1_.player_id from Player players1_ where team0_.id=players1_.team_id))
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun notEmpty() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select t from Team t where t.players is not empty", Team::class.java)
            // select team0_.id as id1_1_, team0_.name as name2_1_ from Team team0_
            // where exists (select players1_.player_id from Player players1_ where team0_.id=players1_.team_id)
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun exists() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select h from Hotel h where exists (select r from Review r where r.hotel = h) order by h.name",
                Hotel::class.java
            )
            // select hotel0_.id as id1_0_, hotel0_.name as name2_0_ from Hotel hotel0_
            // where exists (select review1_.id from hotel_review review1_ where review1_.hotel_id=hotel0_.id)
            // order by hotel0_.name
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun all() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select t from Team t where 500 < all (select p.salary from Player p where p.team = t)",
                Team::class.java
            )
            // select team0_.id as id1_4_, team0_.name as name2_4_ from Team team0_
            // where 500<all (select player1_.salary from Player player1_ where player1_.team_id=team0_.id)
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun any() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery(
                "select t from Team t where 500 < any (select p.salary from Player p where p.team = t)",
                Team::class.java
            )
            // select team0_.id as id1_4_, team0_.name as name2_4_ from Team team0_
            // where 500<any (select player1_.salary from Player player1_ where player1_.team_id=team0_.id)
            query.resultList
        } finally {
            em.close()
        }
    }

}
