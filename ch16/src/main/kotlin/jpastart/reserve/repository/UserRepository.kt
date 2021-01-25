package jpastart.reserve.repository

import jpastart.reserve.model.User
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class UserRepository {

    @PersistenceContext
    private lateinit var em: EntityManager

    fun find(email: String): User? = em.find(User::class.java, email)

    fun save(user: User) = em.persist(user)

    fun remove(user: User) = em.remove(user)

    fun findAll(): List<User> = em.createQuery("select u from User u order by u.name", User::class.java).resultList

}
