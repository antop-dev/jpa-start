package jpastart.query

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import jpastart.jpa.reserve.User
import org.junit.jupiter.api.Test

internal class SimpleQueryTest : JpaTestBase() {

    @Test
    fun simpleTypedQuery() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select u from User u", User::class.java)
            // select user0_.email as email1_2_, user0_.create_date as create_d2_2_, user0_.name as name3_2_
            // from User user0_
            query.resultList
        } finally {
            em.close()
        }
    }

    @Test
    fun simpleQuery() {
        val em = EMF.createEntityManager()
        try {
            val query = em.createQuery("select u from User u")
            val users = query.resultList

            for (o in users) {
                val user = o as User
                println("email = ${user.email}, name = ${user.name}")
            }
        } finally {
            em.close()
        }
    }
}
