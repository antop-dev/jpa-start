package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.reserve.Hotel
import jpastart.jpa.reserve.Review
import jpastart.jpa.reserve.User
import jpastart.jpa.team.Player
import jpastart.jpa.team.Team
import org.junit.jupiter.api.Test

class CollectionQueryTest : JpaTestBase() {

    @Test
    fun playerInTeam() {
        val em = EMF.createEntityManager()
        try {
            val player = em.find(Player::class.java, "P1")

            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Team::class.java)
            val t = cq.from(Team::class.java)

            cq.select(t).where(cb.isMember(player, t.get("players")))

            val query = em.createQuery(cq)
            // select player0_.player_id as player_i1_6_0_, player0_.name as name2_6_0_, player0_.salary as
            // salary3_6_0_, player0_.team_id as team_id4_6_0_, team1_.id as id1_7_1_, team1_.name as name2_7_1_
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
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(User::class.java)
            val u = cq.from(User::class.java)

            cq.select(u)
                .where(cb.isMember("부여", u.get("keywords")))
                .orderBy(cb.asc(u.get<String>("name")))

            val query = em.createQuery(cq)
            // select user0_.email as email1_8_, user0_.create_date as create_d2_8_, user0_.name as name3_8_
            // from User user0_ where '부여' in (select keywords1_.keyword from user_keyword keywords1_ where
            // user0_.email=keywords1_.user_email) order by user0_.name asc
            query.resultList
        } finally {
            em.close()
        }
    }


    @Test
    fun empty() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Team::class.java)

            val t = cq.from(Team::class.java)
            cq.select(t).where(cb.isEmpty(t.get("players")))

            val query = em.createQuery(cq)
            // select team0_.id as id1_7_, team0_.name as name2_7_ from Team team0_
            // where not (exists (select players1_.player_id from Player players1_ where team0_.id=players1_.team_id))
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun notEmpty() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Team::class.java)

            val t = cq.from(Team::class.java)
            cq.select(t).where(cb.isNotEmpty(t.get("players")))

            val query = em.createQuery(cq)
            // select team0_.id as id1_7_, team0_.name as name2_7_ from Team team0_ where exists (select players1_.player_id
            // from Player players1_ where team0_.id=players1_.team_id)
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun exists() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Hotel::class.java)
            // select h from Hotel h
            val h = cq.from(Hotel::class.java)
            cq.select(h)
            // exists()에 사용할 서브쿼리 생성
            // (select r from Review r where r.hotel = h)
            val subQuery = cq.subquery(Review::class.java)
            val r = subQuery.from(Review::class.java)
            subQuery.select(r).where(cb.equal(r.get<Hotel>("hotel"), h))
            // exists(서브쿼리) 조건
            val existsPredicate = cb.exists(subQuery)
            // not(existsPredicate), 즉 not exists (서브쿼리)
            cq.where(cb.not(existsPredicate))

            val query = em.createQuery(cq)
            // select hotel0_.id as id1_0_, hotel0_.name as name2_0_ from Hotel hotel0_
            // where not (exists (select review1_.id from hotel_review review1_ where review1_.hotel_id=hotel0_.id))
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun all() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Team::class.java)
            // select t from Team t
            val t = cq.from(Team::class.java)
            cq.select(t)

            // all (select p.salary from Player p where p.team = t
            val subQuery = cq.subquery(Int::class.java)
            val p = subQuery.from(Player::class.java)
            subQuery.select(p.get("salary")).where(cb.equal(p.get<Team>("team"), t))
            val allExp = cb.all(subQuery)

            // where 500 < all (...)
            // 값 자체를 Expression 위치에 줘야 할 때 literal() 사용
            cq.where(cb.lt(cb.literal(500), allExp))

            val query = em.createQuery(cq)
            // select team0_.id as id1_7_, team0_.name as name2_7_ from Team team0_
            // where 500<all (select player1_.salary from Player player1_ where player1_.team_id=team0_.id)
            query.resultList
        } finally {
            em.close()
        }
    }

}
