package jpastart.reserve.repository

import jpastart.JpaTestBase
import jpastart.reserve.model.User_
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.LocalDateTime

class UserRepositoryTest(private val repository: UserRepository) : JpaTestBase() {

    @Test
    fun findByCreateDateAfter() {
        // select user0_.email as email1_1_, user0_.create_date as create_d2_1_, user0_.name as name3_1_
        // from User user0_ where user0_.create_date>'01/02/2016 00:00:00.000'
        repository.findByCreateDateAfter(LocalDateTime.of(2016, 1, 2, 0, 0, 0))
    }

    @Test
    fun findByNameStartingWithOrderByNameAsc() {
        // select user0_.email as email1_1_, user0_.create_date as create_d2_1_, user0_.name as name3_1_
        // from User user0_ where user0_.name like '고%' escape '\' order by user0_.name asc
        repository.findByNameStartingWithOrderByNameAsc("고")
    }

    @Test
    fun findByNameStartingWithOrderByNameAscCreateDateDesc() {
        // select user0_.email as email1_1_, user0_.create_date as create_d2_1_, user0_.name as name3_1_
        // from User user0_ where user0_.name like '고%' escape '\'
        // order by user0_.name asc, user0_.create_date desc
        repository.findByNameStartingWithOrderByNameAscCreateDateDesc("고")
    }

    @Test
    fun findAll() {
        // select user0_.email as email1_1_, user0_.create_date as create_d2_1_, user0_.name as name3_1_
        // from User user0_ order by user0_.name asc, user0_.create_date desc
        repository.findAll(
            Sort.by(
                Sort.Order(Sort.Direction.ASC, User_.NAME),
                Sort.Order(Sort.Direction.DESC, User_.CREATE_DATE)
            )
        )
    }

    @Test
    fun findByNameStartingWith() {
        val pageable = PageRequest.of(1, 10)
        // select user0_.email as email1_1_, user0_.create_date as create_d2_1_, user0_.name as name3_1_
        // from User user0_ where user0_.name like '최%' escape '\' limit 10, 10
        repository.findByNameStartingWith("최", pageable)
    }

}
