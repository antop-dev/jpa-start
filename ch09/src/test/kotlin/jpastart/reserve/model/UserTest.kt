package jpastart.reserve.model

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UserTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val user = User("user@email.com", "사용자", LocalDateTime.now()).apply {
                keywords = mutableSetOf("역사", "유적", "전통임식")
            }
            em.persist(user)

            // insert into User (create_date, name, email) values ('01/14/2021 09:26:52.139', '사용자', 'user@email.com')
            // insert into user_keyword (user_email, keyword) values ('user@email.com', '역사')
            // insert into user_keyword (user_email, keyword) values ('user@email.com', '유적')
            // insert into user_keyword (user_email, keyword) values ('user@email.com', '전통임식')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun changeKeyword() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val user = em.find(User::class.java, "gildong@dooly.net")
            val keywords = user.keywords
            keywords.remove("서울")
            keywords += "한성"

            // delete from user_keyword where user_email='gildong@dooly.net' and keyword='서울'
            // insert into user_keyword (user_email, keyword) values ('gildong@dooly.net', '한성')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun changeKeywords() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val user = em.find(User::class.java, "gildong@dooly.net")
            user.keywords = mutableSetOf("한성", "부여")

            // delete from user_keyword where user_email='gildong@dooly.net'
            // insert into user_keyword (user_email, keyword) values ('gildong@dooly.net', '한성')
            // insert into user_keyword (user_email, keyword) values ('gildong@dooly.net', '부여')
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun removeKeywords() {
        val em = EMF.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()

            val user = em.find(User::class.java, "gildong@dooly.net")
            user.keywords.clear()

            // delete from user_keyword where user_email='gildong@dooly.net'
            tx.commit()
        } catch (e: Exception) {
            tx.rollback()
            throw e
        } finally {
            em.close()
        }
    }
}
