package jpastart.reserve.repository

import jpastart.reserve.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface UserRepository : JpaRepository<User, String> {

    fun findByEmail(email: String): User?

    fun save(user: User)

    // JpaRepository에 있음
//    fun delete(user: User)

//    fun findAll(): List<User>

    fun findByCreateDateAfter(date: LocalDateTime): List<User>

    fun findByNameStartingWithOrderByNameAsc(name: String): List<User>

    fun findByNameStartingWithOrderByNameAscCreateDateDesc(name: String): List<User>

//    fun findAll(sort: Sort): List<User>

    fun findByNameStartingWith(name: String, pageable: Pageable): List<User>

}
