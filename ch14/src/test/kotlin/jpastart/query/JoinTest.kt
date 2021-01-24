package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.guide.UserBestSight
import jpastart.jpa.reserve.User
import jpastart.jpa.team.Player
import jpastart.jpa.team.Team
import org.junit.jupiter.api.Test
import javax.persistence.criteria.JoinType

class JoinTest : JpaTestBase() {

    @Test
    fun implicitJoin() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Player::class.java)
            val p = cq.from(Player::class.java)

            cq.select(p)
                .where(cb.equal(p.get<Team>("team").get<String>("name"), "팀1"))
                .orderBy(cb.asc(p.get<String>("name")))

            val query = em.createQuery(cq)
            // select player0_.player_id as player_i1_6_, player0_.name as name2_6_, player0_.salary as salary3_6_,
            // player0_.team_id as team_id4_6_ from Player player0_ cross join Team team1_
            // where player0_.team_id=team1_.id and team1_.name='팀1' order by player0_.name asc
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun join() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Player::class.java)
            val p = cq.from(Player::class.java)
            cq.select(p)

            val t = p.join<Player, Team>("team", JoinType.LEFT) // Player 엔티티의 team 속성
            cq.where(cb.equal(t.get<String>("name"), "팀1"))
            cq.orderBy(cb.asc(p.get<String>("name")))

            val query = em.createQuery(cq)
            // select player0_.player_id as player_i1_6_, player0_.name as name2_6_, player0_.salary as salary3_6_,
            // player0_.team_id as team_id4_6_ from Player player0_ left outer join Team team1_ on player0_.team_id=team1_.id
            // where team1_.name='팀1' order by player0_.name asc
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun joinOn() {
        val em = EMF.createEntityManager()
        try {
            val team = em.find(Team::class.java, "T1")

            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Player::class.java)

            val p = cq.from(Player::class.java)
            val t = p.join<Player, Team>("team")
            // on 절에 조건 추가
            t.on(cb.equal(t, team))

            cq.select(p)
            cq.orderBy(cb.asc(p.get<String>("name")))

            val query = em.createQuery(cq)
            // select player0_.player_id as player_i1_6_, player0_.name as name2_6_, player0_.salary as salary3_6_,
            // player0_.team_id as team_id4_6_ from Player player0_ inner join Team team1_
            // on player0_.team_id=team1_.id and (team1_.id='T1') order by player0_.name asc
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun joinWhere() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Array<Any>::class.java)

            val u = cq.from(User::class.java)
            val ubs = cq.from(UserBestSight::class.java)

            cq.where(cb.equal(u.get<String>("email"), ubs.get<String>("email")))
            cq.multiselect(u, ubs)
            cq.orderBy(cb.asc(u.get<String>("name")))

            val query = em.createQuery(cq)
            // select user0_.email as email1_8_0_, userbestsi1_.email as email1_9_1_, user0_.create_date as
            // create_d2_8_0_, user0_.name as name3_8_0_, userbestsi1_.description as descript2_9_1_, userbestsi1_.title
            // as title3_9_1_ from User user0_ cross join user_best_sight userbestsi1_ where user0_.email=userbestsi1_.email
            // order by user0_.name asc
            val rows = query.resultList
            for (row in rows) {
                val user = row[0] as User
                val ubs = row[1] as UserBestSight
                println("user[email=${user.email}, name=${user.name}], ubs[title=${ubs.title}]")
            }

        } finally {
            em.close()
        }
    }

}
