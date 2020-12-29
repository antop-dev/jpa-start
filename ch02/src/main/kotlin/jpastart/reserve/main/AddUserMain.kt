package jpastart.reserve.main

import jpastart.reserve.model.User
import java.time.LocalDateTime
import javax.persistence.Persistence

fun main() {
    val emf = Persistence.createEntityManagerFactory("jpastart")
    val em = emf.createEntityManager()
    val tx = em.transaction

    try {
        tx.begin()
        val user = User("user@user.com", "user", LocalDateTime.now())
        em.persist(user)
        tx.commit()
    } catch (e: Exception) {
        e.printStackTrace()
        tx.rollback()
    } finally {
        em.close()
    }

    emf.close()
}
