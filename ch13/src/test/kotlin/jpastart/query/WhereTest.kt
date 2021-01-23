package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.team.Player
import jpastart.jpa.team.Team
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class WhereTest : JpaTestBase() {

    @Test
    fun positionParameter() {
        val em = EMF.createEntityManager()
        try {
            val query =
                em.createQuery("select p from Player p where p.team.id = ?1 and p.salary > ?2", Player::class.java)
            query.setParameter(1, "T1")
            query.setParameter(2, 1000)
            // select player0_.player_id as player_i1_0_, player0_.name as name2_0_, player0_.salary as salary3_0_,
            // player0_.team_id as team_id4_0_ from Player player0_ where player0_.team_id='T1' and player0_.salary>1000
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun namedParameter() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select p from Player p where p.team.id = :teamId and p.salary > :minSalary")
            query.setParameter("teamId", "T1")
            query.setParameter("minSalary", 100)

            // select player0_.player_id as player_i1_0_, player0_.name as name2_0_, player0_.salary as salary3_0_,
            // player0_.team_id as team_id4_0_ from Player player0_ where player0_.team_id='T1' and player0_.salary>100
            query.resultList
            // 연관 관계에 의해 아래 쿼리가 실행됨
            // select team0_.id as id1_1_0_, team0_.name as name2_1_0_ from Team team0_ where team0_.id='T1'
        } finally {
            em.close()
        }
    }

    @Test
    fun entityParameter() {
        val em = EMF.createEntityManager()
        try {
            val team = em.find(Team::class.java, "T1")
            val query = em.createQuery("select p from Player p where p.team = :team and p.salary > :minSalary")
            query.setParameter("team", team)
            query.setParameter("minSalary", 100)

            // select player0_.player_id as player_i1_0_, player0_.name as name2_0_, player0_.salary as salary3_0_,
            // player0_.team_id as team_id4_0_ from Player player0_ where player0_.team_id='T1' and player0_.salary>100
            query.resultList
            // 연관 관계에 의해 팀을 조회하는 쿼리가 실행되지 않음
        } finally {
            em.close()
        }
    }

    @Test
    fun temporal() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select u from User u where u.createTime < ?1 order by u.email")
            query.setParameter(1, LocalDateTime.now())

            // select user0_.email as email1_2_, user0_.create_date as create_d2_2_, user0_.name as name3_2_
            // from User user0_ where user0_.create_date<'01/23/2021 08:54:04.733' order by user0_.email
            query.resultList
        } finally {
            em.close()
        }
    }


}
