package jpastart.authlog

import jpastart.JpaTestBase
import jpastart.jpa.EMF
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import java.net.InetAddress
import java.time.LocalDateTime

internal class AuthLogTest : JpaTestBase() {

    @Test
    fun persist() {
        val em = EMF.createEntityManager()
        try {
            em.transaction.begin()

            val ipAddress = InetAddress.getByName("192.168.20.1")
            val authLog = AuthLog("antop", ipAddress, LocalDateTime.now(), true)
            em.persist(authLog)
            // insert into auth_log (ipAddress, success, timestamp, userId) values ('192.168.20.1', 1, '01/26/2021 08:44:02.543', 'antop')
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            throw e
        } finally {
            em.close()
        }
    }

    @Test
    fun findById() {
        val em = EMF.createEntityManager()
        try {
            val authLog = em.find(AuthLog::class.java, 1L)
            assertThat(authLog.ipAddress, `is`(InetAddress.getByName("127.0.0.1")))
        } finally {
            em.close()
        }
    }
}
