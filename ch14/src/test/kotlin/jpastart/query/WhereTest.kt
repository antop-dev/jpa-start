package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.guide.UserBestSight
import jpastart.jpa.reserve.User
import jpastart.jpa.team.Player
import jpastart.jpa.team.Team
import org.junit.jupiter.api.Test
import javax.persistence.criteria.Predicate

class WhereTest : JpaTestBase() {

    @Test
    fun equal() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder

            val cq = cb.createQuery(Team::class.java)
            val root = cq.from(Team::class.java)
            cq.select(root).where(cb.equal(root.get<String>("name"), "고길동"))

            val query = em.createQuery(cq)
            // select team0_.id as id1_7_, team0_.name as name2_7_ from Team team0_ where team0_.name='고길동'
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun length() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder

            val cq = cb.createQuery(User::class.java)
            val root = cq.from(User::class.java)
            cq.select(root).where(cb.gt(cb.length(root.get("email")), 15))

            val query = em.createQuery(cq)
            // select user0_.email as email1_8_, user0_.create_date as create_d2_8_, user0_.name as name3_8_
            // from User user0_
            // where length(user0_.email)>15
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun memberOf() {
        val em = EMF.createEntityManager()
        try {
            val player = em.find(Player::class.java, "P1")

            val cb = em.criteriaBuilder
            val cq = cb.createQuery(Team::class.java)

            val t = cq.from(Team::class.java)
            cq.select(t).where(cb.isMember(player, t.get("players")))

            val query = em.createQuery(cq)
            // select team0_.id as id1_7_, team0_.name as name2_7_ from Team team0_
            // where 'P1' in (select players1_.player_id from Player players1_ where team0_.id=players1_.team_id)
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun and() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(UserBestSight::class.java)
            val ubs = cq.from(UserBestSight::class.java)

            val emailPredicate = cb.equal(ubs.get<String>("email"), "madvirus@madvirus.net")
            val titlePredicate = cb.like(ubs.get("title"), "%랜드")
            val andPredicate = cb.and(emailPredicate, titlePredicate)
            cq.where(andPredicate)

            val query = em.createQuery(cq)
            // select userbestsi0_.email as email1_9_, userbestsi0_.description as descript2_9_, userbestsi0_.title
            // as title3_9_ from user_best_sight userbestsi0_ where userbestsi0_.email='madvirus@madvirus.net'
            // and (userbestsi0_.title like '%랜드')
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun conjunction() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(UserBestSight::class.java)
            val ubs = cq.from(UserBestSight::class.java)

            val email: String? = "madvirus@madvirus.net"
            val keyword: String? = "랜드"

            var p = cb.conjunction()
            if (email != null) p = cb.and(p, cb.equal(ubs.get<String>("email"), email))
            if (keyword != null) p = cb.and(p, cb.like(ubs.get("title"), "%$keyword"))
            cq.where(p)

            val query = em.createQuery(cq)
            // select userbestsi0_.email as email1_9_, userbestsi0_.description as descript2_9_, userbestsi0_.title
            // as title3_9_ from user_best_sight userbestsi0_ where 1=1 and userbestsi0_.email='madvirus@madvirus.net'
            // and (userbestsi0_.title like '%랜드')
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun conjunctionArray() {
        val em = EMF.createEntityManager()
        try {
            val cb = em.criteriaBuilder
            val cq = cb.createQuery(UserBestSight::class.java)
            val ubs = cq.from(UserBestSight::class.java)

            val email: String? = null
            val keyword: String? = "랜드"

            val predicates = mutableListOf<Predicate>()
            if (email != null) predicates += cb.equal(ubs.get<String>("email"), email)
            if (keyword != null) predicates += cb.like(ubs.get("title"), "%$keyword")
            cq.where(*predicates.toTypedArray())

            val query = em.createQuery(cq)
            // select userbestsi0_.email as email1_9_, userbestsi0_.description as descript2_9_, userbestsi0_.title
            // as title3_9_ from user_best_sight userbestsi0_ where userbestsi0_.title like '%랜드'
            query.resultList
        } finally {
            em.close()
        }
    }

}
